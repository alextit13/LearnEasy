package com.learn.easy.ui.running_string

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RunningStringViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Running string"
    }
    val text: LiveData<String> = _text
}