package com.learn.easy.ui.video_note

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

    private fun openNote(note: VideoNote) {
        findNavController().navigate(
            R.id.action_nav_video_notes_to_showNoteFragment,
            Bundle().apply {
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

            showDialog.observe(this@VideoNoteFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    showDeleteDialog(it.content())
                }
            })

            showToast.observe(this@VideoNoteFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
                }
            })

            list.observe(this@VideoNoteFragment, Observer {
                if (adapter == null) {
                    adapter = VideoNoteAdapter(viewModel.getNotes(), {
                        openNote(it)
                    }, {
                        viewModel.onLongClick(it)
                    })
                } else {
                    adapter?.notes = it
                }
                rvVideoNotes.adapter = adapter
                adapter?.notifyDataSetChanged()
            })

            viewWasInit()
        }
    }

    private fun showDeleteDialog(note: VideoNote) {
        activity?.let { AlertDialog.Builder(it) }
            ?.setTitle(getString(R.string.delete))
            ?.setMessage(getString(R.string.delete_video_note))
            ?.setPositiveButton(
                getString(R.string.yes)
            ) { p0, _ ->
                viewModel.onClickDelete(note)
                p0.dismiss()
            }
            ?.setNegativeButton(getString(R.string.cancel)) { p0, _ ->
                p0.dismiss()
            }?.create()?.show()
    }

    private fun openAddFragment() {
        findNavController().navigate(R.id.action_nav_video_notes_to_addVideoNoteFragment)
    }
}
