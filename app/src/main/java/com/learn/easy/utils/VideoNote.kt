package com.learn.easy.utils

import java.io.Serializable

data class VideoNote(
    val date: String,
    val title: String,
    val subtitle: String,
    val firstChar: String,
    val pathVideo: String
): Serializable