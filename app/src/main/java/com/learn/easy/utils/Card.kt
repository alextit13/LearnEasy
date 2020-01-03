package com.learn.easy.utils

import java.io.Serializable

data class Card(
    val id: String,
    val title: String,
    val description: String,
    val imagePath: String,

    val audioPath: String,
    val videoPath: String
): Serializable