package com.learn.easy.ui.check_memory

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.SeekBar
import androidx.core.text.clearSpans
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import com.learn.easy.utils.getInterval
import com.learn.easy.utils.lastBreakIndex
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
            textLiveData.observe(this@CheckMemoryFragment, Observer {
                lastBreakIndex = 0
                tvCheckMemory.apply {
                    text = it
                }
            })
            pauseLiveData.observe(this@CheckMemoryFragment, Observer {
                // todo this
            })
            markLiveData.observe(this@CheckMemoryFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    setMarkWord()
                }
            })
            viewWasInit()
        }
        initClickers()
    }

    private fun setMarkWord() {
        val interval = getInterval(tvCheckMemory)
        val ss = SpannableString(tvCheckMemory.text)
        ss.clearSpans()
        ss.setSpan(
            BackgroundColorSpan(Color.parseColor("#9E0046BE")),
            interval.first,
            interval.second,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvCheckMemory.text = ss
    }

    private fun initClickers() {
        seekBarCheckMemory.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, position: Int, isUser: Boolean) {
                if (isUser) {
                    tvTitleCheckMemory.text = "${getString(R.string.speed)} $position"
                    viewModel.onSpeedChanged(position)
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