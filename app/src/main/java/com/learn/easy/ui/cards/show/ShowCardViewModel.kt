package com.learn.easy.ui.cards.show

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.utils.Card
import com.learn.easy.utils.SingleEvent
import java.lang.Exception
import java.lang.IllegalStateException

class ShowCardViewModel(app: Application) : AndroidViewModel(app) {

    val cardLiveData = MutableLiveData<Card>()
    val videoHandler = MutableLiveData<SingleEvent<Boolean>>()

    private val mediaPlayer = MediaPlayer()
    private var isVideoRuning = true

    fun viewWasInit(card: Card) {
        cardLiveData.value = card
    }

    fun onClickPlay() {
        mediaPlayer.stop()
        try {
            mediaPlayer.setDataSource(cardLiveData.value?.audioPath)
        } catch (e: Exception) {
            return
        }
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    fun onClickStop() {
        mediaPlayer.stop()
    }

    fun onClickVideo() {
        if (isVideoRuning) {
            stopVideo()
        } else {
            playVideo()
        }
        isVideoRuning = !isVideoRuning
    }

    private fun stopVideo() {
        videoHandler.value = SingleEvent(false)
    }

    private fun playVideo() {
        videoHandler.value = SingleEvent(true)
    }
}