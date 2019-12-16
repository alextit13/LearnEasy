package com.learn.easy.ui.word_running_top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordRunningTopViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is running top words"
    }
    val text: LiveData<String> = _text
}