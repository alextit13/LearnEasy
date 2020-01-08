package com.learn.easy.ui.word_running_top

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.easy.BaseViewModel
import com.learn.easy.R
import com.learn.easy.utils.FileChooserService
import com.learn.easy.utils.SingleEvent
import java.io.File

class WordRunningTopViewModel(app: Application) : BaseViewModel(app) {

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val dataText = MutableLiveData<String>()

    fun onClickOpenFile() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onSelectDocumentResult(doc: Array<String>) {
        if (doc.isEmpty()) {
            showToast(app.getString(R.string.select_doc))
            return
        }
        dataText.value = FileChooserService.newInstance().getStringFromFile(File(doc.first()))
    }
}