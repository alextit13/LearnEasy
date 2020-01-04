package com.learn.easy.ui.cards.show

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.utils.Card
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_show_card.*
import java.io.File

class ShowCardFragment : Fragment(R.layout.fragment_show_card) {

    private val viewModel: ShowCardViewModel by lazy {
        ViewModelProviders.of(this).get(ShowCardViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataInViews()
        initListeners()
    }

    private fun initDataInViews() {
        viewModel.apply {
            cardLiveData.observe(this@ShowCardFragment, Observer {
                setDataInViews(it)
            })
            videoHandler.observe(this@ShowCardFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    handleVideo(it.content())
                }
            })

            viewWasInit(arguments?.getSerializable(TAG_CARD) as Card)
        }
    }

    private fun handleVideo(play: Boolean) {
        if (play) {
            playVideo()
        } else {
            stopVideo()
        }
    }

    private fun setDataInViews(card: Card) {
        val videoPath = card.videoPath
        if (videoPath.isNotEmpty()) {
            videoViewCard.setVideoPath(card.videoPath)
            videoViewCard.start()
        } else {
            videoViewCard.visibility = View.GONE
        }

        val imagePath = card.imagePath
        if (imagePath.isNotEmpty()) {
            Picasso.get().load(File(imagePath)).into(ivCard)
        } else {
            ivCard.visibility = View.GONE
        }
    }

    private fun playVideo() {
        val pathVideo = viewModel.cardLiveData.value?.videoPath
        videoViewCard.stopPlayback()
        videoViewCard.setVideoPath(pathVideo)
        videoViewCard.start()
    }

    private fun stopVideo(){
        videoViewCard.stopPlayback()
    }

    private fun initListeners() {
        videoViewCard.setOnClickListener { viewModel.onClickVideo() }
        playAudio.setOnClickListener { viewModel.onClickPlay() }
        stopAudio.setOnClickListener { viewModel.onClickStop() }
    }

    companion object {
        const val TAG_CARD = "tag_card"
    }
}
