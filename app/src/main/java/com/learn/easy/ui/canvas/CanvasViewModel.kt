package com.learn.easy.ui.canvas

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.easy.R
import com.learn.easy.utils.DrawingView.DEFAULT_WIDTH
import com.learn.easy.utils.SingleEvent

class CanvasViewModel(val app: Application) : AndroidViewModel(app) {

    val clear = MutableLiveData<SingleEvent<Boolean>>()

    val colorPicker = MutableLiveData<SingleEvent<Boolean>>()
    val colorPosition = MutableLiveData<SingleEvent<Int>>(SingleEvent(0))
    val colorSelect = MutableLiveData<SingleEvent<Int>>(SingleEvent(Color.BLACK))

    val widthPicker = MutableLiveData<SingleEvent<Boolean>>()
    val widthPosition = MutableLiveData<SingleEvent<Int>>(SingleEvent(0))
    val widthSelect = MutableLiveData<SingleEvent<Float>>(SingleEvent(DEFAULT_WIDTH))

    val toast = MutableLiveData<SingleEvent<String>>()
    val savePaint = MutableLiveData<SingleEvent<String>>()

    fun onClickBtnSave(name: String) {
        if (name.isEmpty()) {
            toast.value = SingleEvent(app.getString(R.string.enter_name))
        } else {
            savePint(name)
        }
    }

    private fun savePint(name: String) {
        savePaint.value = SingleEvent(name)
    }

    fun onClickColor() {
        colorPicker.value = SingleEvent(true)
    }

    fun onClickWidth() {
        widthPicker.value = SingleEvent(true)
    }

    fun onClickClear() {
        clear.value = SingleEvent(true)
    }

    fun colorWasChooses(position: Int) {
        colorPosition.value = SingleEvent(position)
        var color = Color.BLACK
        when (position) {
            0 -> color = Color.BLACK
            1 -> color = Color.RED
            2 -> color = Color.GREEN
            3 -> color = Color.BLUE
            4 -> color = Color.WHITE
            5 -> color = Color.YELLOW
        }

        colorSelect.value = SingleEvent(color)
    }

    fun widthWasChooses(position: Int) {
        widthPosition.value = SingleEvent(position)
        var width = DEFAULT_WIDTH
        when (position) {
            0 -> width = DEFAULT_WIDTH
            1 -> width = DEFAULT_WIDTH * 1.1f
            2 -> width = DEFAULT_WIDTH * 1.4f
            3 -> width = DEFAULT_WIDTH * 1.6f
            4 -> width = DEFAULT_WIDTH * 1.8f
            6 -> width = DEFAULT_WIDTH * 2f
            7 -> width = DEFAULT_WIDTH * 2.2f
            8 -> width = DEFAULT_WIDTH * 2.4f
            9 -> width = DEFAULT_WIDTH * 2.6f
            10 -> width = DEFAULT_WIDTH * 2.8f
            11 -> width = DEFAULT_WIDTH * 3f
        }

        widthSelect.value = SingleEvent(width)
    }
}