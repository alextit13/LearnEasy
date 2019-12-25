package com.learn.easy.ui.check_memory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.easy.utils.SingleEvent
import com.learn.easy.utils.addBreaks
import com.learn.easy.utils.createPages
import com.learn.easy.utils.pages
import kotlinx.coroutines.*
import java.util.*

class CheckMemoryViewModel : ViewModel() {

    private val minSpeed = 15_000
    private val maxSpeed = 60_000

    private var pause = true
    private var lastPosition = 0
    private var speed: Int = 500 // 100 - 1000

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val textLiveData = MutableLiveData<String>()
    val pauseLiveData = MutableLiveData<SingleEvent<Boolean>>(SingleEvent(true))
    val markLiveData = MutableLiveData<SingleEvent<Long>>()

    fun viewWasInit() {
        pauseLiveData.value = SingleEvent(true)
        createPages(testString())
        textLiveData.value = pages[0].text.addBreaks()
    }

    fun onSpeedChanged(position: Int) {
        speed = maxSpeed - ((maxSpeed - minSpeed) / 100) * position
    }

    fun onClickStop() {
        pause = true
        playBook(0)
    }

    fun onClickPause() {
        pause = true
    }

    fun onClickPlay() {
        if (!pause) return

        pause = false

        playBook(lastPosition)
    }

    private var indexPage = 0

    private fun playBook(startPosition: Int) {
        GlobalScope.launch {
            val words = pages[indexPage].text.split(" ")
            for (i in startPosition until words.size - 1) {
                withContext(Dispatchers.Main) {
                    markLiveData.value = SingleEvent(Date().time)
                }
                delay(speed.toLong())
                if (pause) {
                    lastPosition = i
                    break
                }

                if (i == words.size - 2) {
                    indexPage++
                    withContext(Dispatchers.Main) {
                        if (indexPage < pages.size - 1)
                            textLiveData.value = pages[indexPage].text.addBreaks()
                    }
                    playBook(indexPage)
                }
            }
        }
    }

    fun onClickOpenFile() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onSelectDocumentResult(doc: Array<out String>) {
        // todo this
    }

    private fun testString(): String {
        return "История с выходным 31 декабря закрутилась еще в конце октября. Тогда появилась петиция о том"
    }
}