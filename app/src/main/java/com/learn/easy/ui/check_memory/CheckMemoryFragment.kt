package com.learn.easy.ui.check_memory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_check_memory.*

class CheckMemoryFragment : BaseFragment(R.layout.fragment_check_memory) {

    private val viewModel: CheckMemoryViewModel by lazy {
        ViewModelProviders.of(this).get(CheckMemoryViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            chooserLiveData.observe(this@CheckMemoryFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    selectFile {
                        viewModel.onSelectDocumentResult(it)
                    }
                }
            })
            speedLiveData.observe(this@CheckMemoryFragment, Observer {
                tvCheckMemory.apply {
                    // todo this
                }
            })
            textLiveData.observe(this@CheckMemoryFragment, Observer {
                tvCheckMemory.apply {
                    text = it
                }
            })
            pauseLiveData.observe(this@CheckMemoryFragment, Observer {
                // todo this
            })
            viewWasInit()
        }
        initClickers()
    }

    private fun initClickers() {
        seekBarCheckMemory.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, position: Int, isUser: Boolean) {
                if (isUser) {
                    // todo this
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        ivStopCheckMemory.setOnClickListener { viewModel.onClickStop() }
        ivPauseCheckMemory.setOnClickListener { viewModel.onClickPause() }
        ivPlayCheckMemory.setOnClickListener { viewModel.onClickPlay() }
        fabCheckMemory.setOnClickListener { viewModel.onClickOpenFile() }
    }
}