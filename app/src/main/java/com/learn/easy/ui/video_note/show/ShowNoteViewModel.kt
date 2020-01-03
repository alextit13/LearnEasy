package com.learn.easy.ui.video_note.show

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.utils.SingleEvent
import com.learn.easy.utils.VideoNote

class ShowNoteViewModel(private val app: Application) : AndroidViewModel(app) {

    private var videoNote: VideoNote? = null
    val playVideo = MutableLiveData<SingleEvent<VideoNote>>()

    fun setVideoNoteFromSerializable(note: VideoNote?) {
        videoNote = note

        if (videoNote != null) {
            playVideo.value = SingleEvent(videoNote!!)
        }
    }
}