package com.learn.easy.ui.word_running_top

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_word_running_top.*
import kotlinx.coroutines.*
import java.lang.Exception

class WordRunningTopFragment : BaseFragment(R.layout.fragment_word_running_top) {

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
        fabOpenStringRunningTop.setOnClickListener {  }
        tvTextStringRunningTop.apply {
            text = "You are almost there. Just add a few more things to achieve this, First of all add those properties in your TextView You are almost there. Just add a few more things to achieve this, First of all add those properties in your TextViewYou are almost there. Just add a few more things to achieve this, First of all add those properties in your TextViewYou are almost there. Just add a few more things to achieve this, First of all add those properties in your TextViewYou are almost there. Just add a few more things to achieve this, First of all add those properties in your TextViewYou are almost there. Just add a few more things to achieve this, First of all add those properties in your TextViewYou are almost there. Just add a few more things to achieve this, First of all add those properties in your TextViewYou are almost there. Just add a few more things to achieve this, First of all add those properties in your TextView"
        }
        svWordRunningTop.fullScroll(View.FOCUS_DOWN)
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
}