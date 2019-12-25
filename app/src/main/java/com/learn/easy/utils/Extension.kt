package com.learn.easy.utils

fun String.addBreaks(): String {
    val listWords = this.split(" ")
    var result = ""

    for (word in listWords) {
        result = result + word + "\n"
    }
    return result
}