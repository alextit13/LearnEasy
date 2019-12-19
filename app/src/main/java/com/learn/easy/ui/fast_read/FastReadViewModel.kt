package com.learn.easy.ui.fast_read

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.easy.utils.DocFastReader
import com.learn.easy.utils.SingleEvent
import kotlinx.coroutines.*

class FastReadViewModel : ViewModel() {

    val wordLiveData = MutableLiveData<String>() // todo implement this
    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val speedViewModel = MutableLiveData(50)

    private var book: DocFastReader? = null
    private var pause = true
    private var lastPosition = 0
    private var speed: Long = 500L // 100 - 1000

    fun viewWasInit() {
        // todo this
    }

    fun onSpeedChanged(position: Int) {
        speedViewModel.value = position // 1000 - 40 * 10
        speed = 1000L - (position * 10)
    }

    fun onClickStop() {
        pause = true
        playBook(0)
    }

    fun onClickPause() {
        pause = true
    }

    fun onClickPlay() {
        book = DocFastReader().apply {
            words = testString().split(" ").toMutableList()
        }

        if (!pause) return

        pause = false
        playBook(lastPosition)
    }

    private fun playBook(startPosition: Int) {
        GlobalScope.launch {
            val words = book?.words ?: mutableListOf()
            for (i in startPosition until words.size) {
                withContext(Dispatchers.Main) {
                    wordLiveData.value = words[i]
                }
                delay(speed)
                if (pause) {
                    lastPosition = i
                    break
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
        return "История с выходным 31 декабря закрутилась еще в конце октября. Тогда появилась петиция о том, чтобы сделать выходными не только 1 и 2 января, но также 31 декабря, и не отрабатывать эти дни в субботы. Петицию подписали более 15 тысяч человек. В начале ноября премьер-министр Сергей Румас пообещал рассмотреть это предложение, а 6 декабря рассказал, к какому решению пришли чиновники. Вот и все"
    }
}