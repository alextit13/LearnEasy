package com.learn.easy.ui.canvas

import java.io.File

interface ISavePaintCallback {
    fun saveSuccess(fileImage: File, nameImage: String)
}