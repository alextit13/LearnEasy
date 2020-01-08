package com.learn.easy.ui.fast_read

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.learn.easy.BaseViewModel
import com.learn.easy.R
import com.learn.easy.utils.DocFastReader
import com.learn.easy.utils.FileChooserService
import com.learn.easy.utils.SingleEvent
import kotlinx.coroutines.*
import java.io.File

class FastReadViewModel(app: Application) : BaseViewModel(app) {

    val wordLiveData = MutableLiveData<String>() // todo implement this
    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val speedViewModel = MutableLiveData(50)

    private var book: DocFastReader? = null
    private var pause = true
    private var lastPosition = 0
    private var speed: Long = 500L // 100 - 1000
    private var dataText = ""

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
        if (dataText == "") {
            showToast(app.getString(R.string.select_doc))
            return
        }

        book = DocFastReader().apply {
            words = dataText.split(" ").toMutableList()
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
        if (doc.isEmpty()) return
        dataText = FileChooserService.newInstance().getStringFromFile(File(doc.first()))
        showToast(app.getString(R.string.click_play_for_start))
    }
}