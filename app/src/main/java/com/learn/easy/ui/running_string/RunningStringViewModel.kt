package com.learn.easy.ui.running_string

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.easy.BaseViewModel
import com.learn.easy.R
import com.learn.easy.utils.DocFastReader
import com.learn.easy.utils.FileChooserService
import com.learn.easy.utils.SingleEvent
import java.io.File

class RunningStringViewModel(app: Application) : BaseViewModel(app) {

    private val minSpeed = 15_000
    private var maxSpeed = 60_000

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val speedLiveData = MutableLiveData(50)
    val textLiveData = MutableLiveData<String>()
    val pauseLiveData = MutableLiveData<SingleEvent<Boolean>>(SingleEvent(true))

    private var dataString = ""

    private var book: DocFastReader? = null

    fun viewWasInit() {
        pauseLiveData.value = SingleEvent(true)
        speedLiveData.value = (maxSpeed - minSpeed) / 2
    }

    fun onSpeedChanged(position: Int) {
        val a = maxSpeed - ((maxSpeed - minSpeed) / 100) * position
        speedLiveData.value = a
    }

    fun onClickStop() {
        // todo this
    }

    fun onClickPause() {
        pauseLiveData.value = SingleEvent(true)
    }

    fun onClickPlay() {
        if (dataString == "") {
            showToast(app.getString(R.string.select_doc))
            return
        }
        pauseLiveData.value = SingleEvent(false)
    }

    fun onClickOpenFile() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onSelectDocumentResult(doc: Array<out String>) {
        if (doc.isEmpty()) {
            showToast(app.getString(R.string.select_doc))
            return
        }
        dataString = FileChooserService.newInstance().getStringFromFile(File(doc.first()))
        book = DocFastReader().apply {
            text = dataString
        }
        textLiveData.value = book?.text ?: ""
    }
}