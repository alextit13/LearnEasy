package com.learn.easy.ui.word_running_top

import android.app.Application
import android.util.TypedValue
import androidx.lifecycle.MutableLiveData
import com.learn.easy.BaseViewModel
import com.learn.easy.R
import com.learn.easy.utils.FileChooserService
import com.learn.easy.utils.SingleEvent
import java.io.File

class WordRunningTopViewModel(app: Application) : BaseViewModel(app) {

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val dataText = MutableLiveData<String>()

    private var scrollViewHeight = 0

    fun onClickOpenFile() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onSelectDocumentResult(doc: Array<String>) {
        if (doc.isEmpty()) {
            showToast(app.getString(R.string.select_doc))
            return
        }
        onTextChoose(FileChooserService.newInstance().getStringFromFile(File(doc.first())))
    }

    fun onTextChoose(text: String) {
        val tResult = getAdditionalString() + text + getAdditionalString()
        dataText.value = tResult
    }

    private fun getAdditionalString(): String {
        var t = "\n"
        val numEntered = scrollViewHeight / getWordHeight()
        for (i in 0..numEntered) {
            t += "\n"
        }
        return t
    }

    private fun getWordHeight(): Int {
        return TypedValue
            .applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 28.toFloat(),
                app.resources.displayMetrics
            ).toInt()
    }

    fun onScrollViewWasInit(height: Int) {
        scrollViewHeight = height
    }
}