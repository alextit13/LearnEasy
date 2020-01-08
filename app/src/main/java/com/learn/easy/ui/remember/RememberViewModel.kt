package com.learn.easy.ui.remember

import android.app.Application
import android.text.SpannableString
import androidx.lifecycle.MutableLiveData
import com.learn.easy.BaseViewModel
import com.learn.easy.R
import com.learn.easy.utils.*
import java.io.File

class RememberViewModel(app: Application) : BaseViewModel(app) {

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val textViewModel = MutableLiveData<SpannableString>()
    val currentPageViewModel = MutableLiveData<Pair<Int, Int>>()
    val showWordSuggestionMode = MutableLiveData<SingleEvent<Boolean>>()

    fun isWordSuggestionEnable(): Boolean {
        return showWordSuggestionMode.value?.content() ?: false
    }

    fun onClickShowWordMode() {
        val isWordSuggestion = showWordSuggestionMode.value?.content() ?: false
        showWordSuggestionMode.value = SingleEvent(!isWordSuggestion)
    }

    fun onClickShowAllMode() {
        if (pages.isEmpty()) return
        getSpannableString(pages[currentPage].text, true) {
            textViewModel.value = it
        }
    }

    fun onClickRefreshAll() {
        if (pages.isEmpty()) return
        getSpannableString(pages[currentPage].text, false) {
            textViewModel.value = it
        }
    }

    fun onClickSelectDoc() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onFilesWasChoose(files: Array<out String>) {
        clearData()
        val file = files.first()
        val text = FileChooserService.newInstance().getStringFromFile(File(file))
        if (text != "") {
            makeSpannableString(text)
        } else {
            showToast(app.getString(R.string.error))
        }
    }

    private fun clearData() {
        currentPage = 0
        pagesIterator = 1
        pages.clear()
    }

    fun onClickNextPage() {
        if (pages.isEmpty()) {
            showToast(app.getString(R.string.select_doc))
            return
        }
        if (currentPage == pages.size - 1) return
        currentPage++
        pagesIterator++
        getSpannableString(pages[currentPage].text, false) {
            textViewModel.value = it
        }
        currentPageViewModel.value = Pair(pagesIterator, pages.size)
    }

    fun onClickPreviousPage() {
        if (currentPage == 0) return
        currentPage--
        pagesIterator--
        getSpannableString(pages[currentPage].text, false) {
            textViewModel.value = it
        }
        currentPageViewModel.value = Pair(pagesIterator, pages.size)
    }

    private fun makeSpannableString(s: String) {
        createPages(s)
        getSpannableString(pages[0].text, false) {
            textViewModel.value = it
        }
        currentPageViewModel.value = Pair(pagesIterator, pages.size)
    }
}