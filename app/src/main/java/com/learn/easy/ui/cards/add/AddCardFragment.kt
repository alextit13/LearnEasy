package com.learn.easy.ui.cards.add

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : Fragment(R.layout.fragment_add_card) {

    private val viewModel: AddCardViewModel by lazy {
        ViewModelProviders.of(this).get(AddCardViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initListeners() {
        btnAddVideoRecord.setOnClickListener { viewModel.onClickAddVideo() }
        btnAudioRecord.setOnClickListener { viewModel.onClickAddAudio() }
        btnAddImage.setOnClickListener { viewModel.onClickAddImage() }
        btnCancel.setOnClickListener { viewModel.onClickCancel() }
        btnSave.setOnClickListener {
            viewModel.onClickSave(
                etTitle.text.toString(),
                etDescription.text.toString()
            )
        }
    }

    private fun initObservers() {
        viewModel.apply {
            toast.observe(this@AddCardFragment, Observer {
                Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
            })

            video.observe(this@AddCardFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    getVideo(it.content())
                }
            })

            audio.observe(this@AddCardFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    getAudio(it.content())
                }
            })

            image.observe(this@AddCardFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    getImage(it.content())
                }
            })
            cancel.observe(this@AddCardFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    activity?.onBackPressed()
                }
            })
        }
    }

    private fun getVideo(intent: Intent) {
        startActivityForResult(intent, REQ_VIDEO)
    }

    private fun getAudio(intent: Intent) {
        startActivityForResult(intent, REQ_AUDIO)
    }

    private fun getImage(intent: Intent) {
        val builder = StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build())
        startActivityForResult(intent, REQ_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, data)
    }

    companion object {
        const val REQ_VIDEO = 997
        const val REQ_AUDIO = 998
        const val REQ_IMAGE = 999
    }
}
