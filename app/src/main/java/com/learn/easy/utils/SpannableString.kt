package com.learn.easy.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan

val pages: MutableList<Page> = mutableListOf()
private const val COUNT_WORD_ON_PAGE = 10
var currentPage = 0
var pagesIterator = 1

fun createPages(s: String) {

    pages.clear()
    val words = s.split(" ")
    var tenWords = ""
    var iterator = 0
    var iteratePages = 0
    for ((iteratorLast, element) in words.withIndex()) {

        tenWords = "$tenWords$element "
        iterator++

        if (iterator == COUNT_WORD_ON_PAGE || iteratorLast ==  words.size - 1) {
            val page = Page()
            page.number = iteratePages
            page.text = tenWords

            pages.add(page)
            tenWords = ""
            iterator = 0
            iteratePages++
        }
    }

    println("sdf")
}

fun getWordArray(s: String): MutableList<Int> {
    val indexes = mutableListOf<Int>()
    indexes.add(0)
    for (i in s.toCharArray().indices) {
        if (s.toCharArray()[i] == ' ') {
            indexes.add(i)
        }
    }
    return indexes
}

fun getSpannableString(s: String, callback: (SpannableString) -> Unit) {
    val ss = SpannableString(s)

    var i = 0
    val indexes = getWordArray(s)
    while (i < indexes.size - 1) {
        val p = Pair(indexes[i] + if (i == 0) 1 else 2, indexes[i + 1])
        if (p.first > p.second) continue
        ss.setSpan(
            BackgroundColorSpan(Color.GRAY),
            p.first,
            p.second,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ss.setSpan(
            ForegroundColorSpan(Color.GRAY),
            p.first,
            p.second,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        i += 1
    }

    callback.invoke(ss)
}