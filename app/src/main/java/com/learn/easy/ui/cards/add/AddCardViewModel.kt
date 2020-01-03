package com.learn.easy.ui.cards.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.utils.SingleEvent

class AddCardViewModel(private val app: Application): AndroidViewModel(app) {

    val toast = MutableLiveData<SingleEvent<String>>()

    fun onClickRotate() {

    }
}