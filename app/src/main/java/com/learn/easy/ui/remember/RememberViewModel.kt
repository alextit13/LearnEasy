package com.learn.easy.ui.remember

import android.app.Application
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.utils.*
import java.io.File

class RememberViewModel(app: Application) : AndroidViewModel(app) {

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
        getSpannableString(pages[currentPage].text, true) {
            textViewModel.value = it
        }
    }

    fun onClickRefreshAll() {
        getSpannableString(pages[currentPage].text, false) {
            textViewModel.value = it
        }
    }

    fun onClickSelectDoc() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onFilesWasChoose(files: Array<out String>) {
        val file = files.first()
        val text = FileChooserService.newInstance().getStringFromFile(File(file))
        println("dfsllf;ks")
    }

    fun onClickNextPage() {
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

    fun makeSpannableString(s: String) {
        createPages(s)
        getSpannableString(pages[0].text, false) {
            textViewModel.value = it
        }
        currentPageViewModel.value = Pair(pagesIterator, pages.size)
    }
}