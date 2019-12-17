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

class RememberViewModel(private val app: Application) : AndroidViewModel(app) {

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val textViewModel = MutableLiveData<SpannableString>()
    val currentPageViewModel = MutableLiveData<Pair<Int, Int>>()

    fun onClickShowWordMode() {
        // todo this
    }

    fun onClickShowAllMode() {
        // todo this
    }

    fun onClickRefreshAll() {
        // todo this
    }

    fun onClickSelectDoc() {
        chooserLiveData.value = SingleEvent(true)
    }

    fun onFilesWasChoose(files: Array<out String>) {
        // todo this
    }

    fun onClickNextPage() {
        if (currentPage == pages.size - 1) return
        currentPage++
        pagesIterator++
        getSpannableString(pages[currentPage].text) {
            textViewModel.value = it
        }
        currentPageViewModel.value = Pair(pagesIterator, pages.size)
    }

    fun onClickPreviousPage() {
        if (currentPage == 0) return
        currentPage--
        pagesIterator--
        getSpannableString(pages[currentPage].text) {
            textViewModel.value = it
        }
        currentPageViewModel.value = Pair(pagesIterator, pages.size)
    }

    fun makeSpannableString(s: String) {
        createPages(s)
        getSpannableString(pages[0].text) {
            textViewModel.value = it
        }
        currentPageViewModel.value = Pair(pagesIterator, pages.size)
    }
}