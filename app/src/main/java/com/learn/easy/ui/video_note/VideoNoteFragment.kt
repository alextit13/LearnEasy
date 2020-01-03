package com.learn.easy.ui.video_note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.learn.easy.R
import com.learn.easy.utils.VideoNote
import kotlinx.android.synthetic.main.fragment_video_note.*

class VideoNoteFragment : Fragment(R.layout.fragment_video_note) {

    private var adapter: VideoNoteAdapter? = null

    private val viewModel: VideoNotesViewModel by lazy {
        ViewModelProviders.of(this).get(VideoNotesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
    }

    override fun onResume() {
        super.onResume()

        initAdapter()
    }

    private fun initAdapter() {
        adapter = VideoNoteAdapter(viewModel.getNotes()) {
            openNote(it)
        }
        rvVideoNotes.adapter = adapter
    }

    private fun openNote(note: VideoNote) {
        findNavController().navigate(R.id.action_nav_video_notes_to_showNoteFragment, Bundle().apply {
            putSerializable("tag_note", note)
        })
    }

    private fun initListeners() {
        fabAddVideoNotes.setOnClickListener { viewModel.onClickAdd() }
    }

    private fun initObservers() {
        viewModel.apply {
            openAddScreen.observe(this@VideoNoteFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    openAddFragment()
                }
            })
        }
    }

    private fun openAddFragment() {
        findNavController().navigate(R.id.action_nav_video_notes_to_addVideoNoteFragment)
    }
}
