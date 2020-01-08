package com.learn.easy

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(val app: Application) : AndroidViewModel(app) {

    fun showToast(message: String) {
        Toast.makeText(app, message, Toast.LENGTH_LONG).show()
    }
}