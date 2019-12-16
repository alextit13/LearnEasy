package com.learn.easy.ui.remember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RememberViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Remember"
    }
    val text: LiveData<String> = _text
}