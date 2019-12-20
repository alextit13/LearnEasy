package com.learn.easy.ui.check_memory

import android.text.SpannableString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.easy.utils.DocFastReader
import com.learn.easy.utils.SingleEvent
import com.learn.easy.utils.createPages

class CheckMemoryViewModel : ViewModel() {

    private val minSpeed = 15_000
    private val maxSpeed = 60_000

    private var pause = true

    val chooserLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val speedLiveData = MutableLiveData(50)
    val textLiveData = MutableLiveData<String>()
    val pauseLiveData = MutableLiveData<SingleEvent<Boolean>>(SingleEvent(true))

    fun viewWasInit() {
        pauseLiveData.value = SingleEvent(true)
        // speedLiveData.value = (maxSpeed - minSpeed) / 2
        createPages(testString())
    }

    fun onSpeedChanged(position: Int) {
        val a = maxSpeed - ((maxSpeed - minSpeed) / 100) * position
        speedLiveData.value = a
    }

    fun onClickStop() {
        // todo this
    }

    fun onClickPause() {
        // todo this
    }

    fun onClickPlay() {
        if (!pause) return

        pause = false


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