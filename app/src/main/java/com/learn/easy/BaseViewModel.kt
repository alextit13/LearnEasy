package com.learn.easy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.utils.SingleEvent

open class BaseViewModel(val app: Application): AndroidViewModel(app) {
    val toast = MutableLiveData<SingleEvent<String>>()
}