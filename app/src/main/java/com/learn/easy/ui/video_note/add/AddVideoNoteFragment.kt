package com.learn.easy.ui.video_note.add

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.utils.SingleEvent
import kotlinx.android.synthetic.main.fragment_add_video_note.*

class AddVideoNoteFragment : Fragment(R.layout.fragment_add_video_note) {

    private val viewModel: AddVideoNoteViewModel by lazy {
        ViewModelProviders.of(this).get(AddVideoNoteViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        btnCancel.setOnClickListener { activity?.onBackPressed() }
        btnSave.setOnClickListener { viewModel.onClickSave(etTitleVideoNotes.text.toString()) }
        cvRecordVideo.setOnClickListener { viewModel.onClickRecordVideo() }
    }

    private fun initObservers() {
        viewModel.apply {
            toast.observe(this@AddVideoNoteFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
                }
            })

            startVideo.observe(this@AddVideoNoteFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    startVideoCapture(it)
                }
            })

            close.observe(this@AddVideoNoteFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    activity?.onBackPressed()
                }
            })
        }
    }

    private fun startVideoCapture(intent: SingleEvent<Intent>?) {
        startActivityForResult(intent?.content(), REQ_CODE_VIDEO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_VIDEO) {
            data?.data?.let { viewModel.onVideoWasRecord(it) }
        }
    }

    companion object {
        private const val REQ_CODE_VIDEO = 334
    }
}