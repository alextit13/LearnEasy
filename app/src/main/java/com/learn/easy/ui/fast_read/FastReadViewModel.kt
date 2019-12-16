package com.learn.easy.ui.fast_read

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FastReadViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Fast reader"
    }
    val text: LiveData<String> = _text
}