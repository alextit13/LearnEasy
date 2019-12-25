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
            for (i in startPosition until words.size - 1) {
                withContext(Dispatchers.Main) {
                    markLiveData.value = SingleEvent(Date().time)
                }
                delay(speed.toLong())
                if (pause) {
                    lastPosition = i
                    break
                }
                if (i == 9) { // 10 - 1 words on one page
                    indexPage++
                    withContext(Dispatchers.Main) {
                        textLiveData.value = pages[indexPage].text.addBreaks()
                        playBook(indexPage)
                    }
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