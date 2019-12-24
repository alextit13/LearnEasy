package com.learn.easy.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.text.clearSpans

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

        if (iterator == COUNT_WORD_ON_PAGE || iteratorLast == words.size - 1) {
            val page = Page()
            page.number = iteratePages
            page.text = tenWords

            pages.add(page)
            tenWords = ""
            iterator = 0
            iteratePages++
        }
    }
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

fun getSpannableString(allText: String, isShowAll: Boolean, callback: (SpannableString) -> Unit) {
    val ss = SpannableString(allText)

    var iteratorWords = 0
    val indexes = getWordArray(allText)
    while (iteratorWords < indexes.size - 1) {
        val p = Pair(
            indexes[iteratorWords] + if (iteratorWords == 0) 1 else 2,
            indexes[iteratorWords + 1]
        )
        if (p.first > p.second) continue
        if (isShowAll) {
            ss.clearSpans()
        } else {
            ss.setSpan(
                object : ClickableSpan() {
                    override fun onClick(p0: View) {}

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.linkColor = Color.GRAY
                        ds.bgColor = Color.GRAY
                        ds.color = Color.GRAY
                    }
                },
                p.first,
                p.second,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        iteratorWords += 1
    }

    callback.invoke(ss)
}

fun clickableSpan(tvRemember: TextView, motionEvent: MotionEvent){
    val mOffset = tvRemember.getOffsetForPosition(motionEvent.x, motionEvent.y)
    val offset = WordFinder.findWordForRightHanded(tvRemember.text.toString(), mOffset)
    val ss = SpannableString(tvRemember.text)
    ss.setSpan(
        object : ClickableSpan() {
            override fun onClick(p0: View) {}

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.linkColor = Color.BLACK
                ds.bgColor = Color.WHITE
                ds.color = Color.BLACK
            }
        },
        offset.startPosition,
        offset.endPosition,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    tvRemember.text = ss
}