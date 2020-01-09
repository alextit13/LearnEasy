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
        tvCheckMemory.dur = 50
        viewModel.apply {
            chooserLiveData.observe(this@RunningStringFragment, Observer { se ->
                if (se.getContentIfNotHandled() != null) {
                    selectFile {
                        viewModel.onSelectDocumentResult(it)
                    }
                }
            })
            speedLiveData.observe(this@RunningStringFragment, Observer {
                tvCheckMemory.apply {
                    dur = it
                    resumeScroll()
                }
            })
            textLiveData.observe(this@RunningStringFragment, Observer {
                tvCheckMemory.apply {
                    text = it
                    startScroll()
                    pauseScroll()
                }
            })
            pauseLiveData.observe(this@RunningStringFragment, Observer {
                if (it.getContentIfNotHandled()!!) {
                    tvCheckMemory.pauseScroll()
                } else {
                    tvCheckMemory.resumeScroll()
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
                    tvCheckMemory.pauseScroll()
                    viewModel.onSpeedChanged(position)
                    tvTitleSpeedRunningString.text =
                        "${activity?.getString(R.string.speed)} $position"
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        ivPauseCheckMemory.setOnClickListener { viewModel.onClickPause() }
        ivPlayCheckMemory.setOnClickListener { viewModel.onClickPlay() }
        fabCheckMemory.setOnClickListener { viewModel.onClickOpenFile() }
    }
}