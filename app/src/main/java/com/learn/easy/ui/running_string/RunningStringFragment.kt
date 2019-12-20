package com.learn.easy.ui.running_string

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_running_string.*

class RunningStringFragment : BaseFragment(R.layout.fragment_running_string) {

    private val viewModel: RunningStringViewModel by lazy {
        ViewModelProviders.of(this).get(RunningStringViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            chooserLiveData.observe(this@RunningStringFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    selectFile {
                        viewModel.onSelectDocumentResult(it)
                    }
                }
            })
            speedLiveData.observe(this@RunningStringFragment, Observer {
                tvFastReaderRunningString.apply {
                    rndDuration = it
                }
            })
            textLiveData.observe(this@RunningStringFragment, Observer {
                tvFastReaderRunningString.apply {
                    text = it
                    startScroll()
                    pauseScroll()
                }
            })
            pauseLiveData.observe(this@RunningStringFragment, Observer {
                if (it.getContentIfNotHandled()!!) {
                    tvFastReaderRunningString.pauseScroll()
                } else {
                    tvFastReaderRunningString.resumeScroll()
                }
            })
            viewWasInit()
        }
        initClickers()
    }

    private fun initClickers() {
        seekBarRunningString.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, position: Int, isUser: Boolean) {
                if (isUser) {
                    tvFastReaderRunningString.pauseScroll()
                    viewModel.onSpeedChanged(position)
                    tvFastReaderRunningString.resumeScroll()
                    tvTitleSpeedRunningString.text = "${activity?.getString(R.string.speed)} $position"
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        ivStopRunningString.setOnClickListener { viewModel.onClickStop() }
        ivPauseRunningString.setOnClickListener { viewModel.onClickPause() }
        ivPlayRunningString.setOnClickListener { viewModel.onClickPlay() }
        fabRunningString.setOnClickListener { viewModel.onClickOpenFile() }
    }
}