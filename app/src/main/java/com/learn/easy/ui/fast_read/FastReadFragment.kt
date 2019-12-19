package com.learn.easy.ui.fast_read

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_fast_read.*

class FastReadFragment : BaseFragment(R.layout.fragment_fast_read) {

    private val viewModel: FastReadViewModel by lazy {
        ViewModelProviders.of(this).get(FastReadViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            viewWasInit()
            wordLiveData.observe(this@FastReadFragment, Observer {
                onTextChanged(it)
            })
            chooserLiveData.observe(this@FastReadFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    selectFile {
                        viewModel
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
        fabFastReader.setOnClickListener { viewModel.onClickOpenFile() }
    }

    private fun onTextChanged(word: String) {
        tvFastReader.text = word
    }
}