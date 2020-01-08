package com.learn.easy.ui.check_memory

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.easy.BaseViewModel
import com.learn.easy.R
import com.learn.easy.utils.*
import kotlinx.coroutines.*
import java.io.File
import java.util.*

class CheckMemoryViewModel(app: Application) : BaseViewModel(app) {

    private val minSpeed = 2_00
    private val maxSpeed = 1_000

    private var pause = true
    private var lastPosition = 0
    private var speed: Int = 500 // 100 - 1000

    private var indexPage = 0

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val textLiveData = MutableLiveData<String>()
    val pauseLiveData = MutableLiveData<SingleEvent<Boolean>>(SingleEvent(true))
    val markLiveData = MutableLiveData<SingleEvent<Long>>()

    private var dataText = ""

    private fun viewWasInit() {
        pauseLiveData.value = SingleEvent(true)
        createPages(dataText)
        textLiveData.value = pages[0].text.addBreaks()
    }

    fun onSpeedChanged(position: Int) {
        speed = maxSpeed - ((maxSpeed - minSpeed) / 100) * position
    }

    fun onClickStop() {
        pause = true
        lastPosition = 0
        indexPage = 0
        textLiveData.value = pages[0].text.addBreaks()
    }

    fun onClickPause() {
        pause = true
    }

    fun onClickPlay() {
        if (!pause) return

        pause = false

        playBook(lastPosition)
    }

    private fun playBook(startPosition: Int) {
        GlobalScope.launch {
            val words = pages[indexPage].text.split(" ")
            for (i in startPosition until words.size) {
                withContext(Dispatchers.Main) {
                    markLiveData.value = SingleEvent(Date().time)
                }
                delay(speed.toLong())
                if (pause) {
                    lastPosition = i
                    break
                }
                if (i == words.size - 1) { // 10 - 1 words on one page
                    indexPage++
                    withContext(Dispatchers.Main) {
                        textLiveData.value = pages[indexPage].text.addBreaks()
                        playBook(0)
                    }
                }
            }
        }
    }

    fun onClickOpenFile() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onSelectDocumentResult(doc: Array<out String>) {
        if (doc.isEmpty()) {
            showToast(app.getString(R.string.select_doc))
            return
        }
        dataText = FileChooserService.newInstance().getStringFromFile(File(doc.first()))
        viewWasInit()
    }
}