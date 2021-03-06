package com.learn.easy.ui.fast_read

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseSelectFragment
import kotlinx.android.synthetic.main.fragment_fast_read.*

class FastReadFragment : BaseSelectFragment(R.layout.fragment_fast_read) {

    private val viewModel: FastReadViewModel by lazy {
        ViewModelProviders.of(this).get(FastReadViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            wordLiveData.observe(this@FastReadFragment, Observer {
                onTextChanged(it)
            })
            chooserLiveData.observe(this@FastReadFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    selectFile {
                        viewModel.onSelectDocumentResult(it)
                    }
                }
            })
            speedViewModel.observe(this@FastReadFragment, Observer {
                tvTitleSpeed.text = "${activity?.getString(R.string.speed)} $it"
                seekBar.progress = it
            })
        }

        initClickers()
    }

    private fun initClickers() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, position: Int, isUser: Boolean) {
                if (isUser) {
                    viewModel.onSpeedChanged(position)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        ivStopFastReader.setOnClickListener { viewModel.onClickStop() }
        ivPauseFastReader.setOnClickListener { viewModel.onClickPause() }
        ivPlayFastReader.setOnClickListener { viewModel.onClickPlay() }
        fabFastReader.setOnClickListener { showChooseDialog() }
    }

    private fun onTextChanged(word: String) {
        tvFastReader.text = word
    }

    override fun onClickEnter() {
        showEnterTextDialog()
    }

    override fun onClickSelect() {
        viewModel.onClickOpenFile()
    }

    override fun onEnterTextResult(text: String) {
        viewModel.onTextChoose(text)
    }
}