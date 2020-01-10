package com.learn.easy.ui.word_running_top

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import com.learn.easy.ui.BaseSelectFragment
import kotlinx.android.synthetic.main.fragment_word_running_top.*
import kotlinx.coroutines.*

class WordRunningTopFragment : BaseSelectFragment(R.layout.fragment_word_running_top) {

    private val viewModel: WordRunningTopViewModel by lazy {
        ViewModelProviders.of(this).get(WordRunningTopViewModel::class.java)
    }

    private var isRunning = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        runScroller()
    }

    private fun initListeners() {
        fabOpenStringRunningTop.setOnClickListener { showChooseDialog() }


        viewModel.chooserLiveData.observe(this, Observer {
            if (it.getContentIfNotHandled() != null) {
                selectFile {
                    viewModel.onSelectDocumentResult(it)
                }
            }
        })
        viewModel.dataText.observe(this, Observer {
            tvTextStringRunningTop.apply {
                text = it
            }
            svWordRunningTop.fullScroll(View.FOCUS_DOWN)
        })
    }

    private fun runScroller() {
        var scrollPosition = 0
        GlobalScope.launch {
            while (isRunning) {
                withContext(Dispatchers.Main) {
                    try {
                        svWordRunningTop.smoothScrollTo(0, scrollPosition)
                    } catch (e: Exception) {
                        println("LOG_TAG: current fragment was closed")
                        isRunning = false
                    }
                }
                delay(10)
                scrollPosition++
            }
        }
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