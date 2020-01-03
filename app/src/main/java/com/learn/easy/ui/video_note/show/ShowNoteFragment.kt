package com.learn.easy.ui.video_note.show

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.utils.VideoNote
import kotlinx.android.synthetic.main.fragment_show_note.*

class ShowNoteFragment : Fragment(R.layout.fragment_show_note) {

    private val viewModel: ShowNoteViewModel by lazy {
        ViewModelProviders.of(this).get(ShowNoteViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setVideoNoteFromSerializable(arguments?.getSerializable("tag_note") as? VideoNote)

        initObservers()
    }

    private fun initObservers() {
        viewModel.apply {
            playVideo.observe(this@ShowNoteFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    videoView.setVideoPath(it.content().pathVideo)
                    tvTitleVideoNote.text = it.content().title
                    videoView.start()
                }
            })
        }
    }
}
