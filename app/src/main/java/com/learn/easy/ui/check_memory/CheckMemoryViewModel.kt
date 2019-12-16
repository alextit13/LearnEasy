package com.learn.easy.ui.check_memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckMemoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Check memory"
    }
    val text: LiveData<String> = _text
}