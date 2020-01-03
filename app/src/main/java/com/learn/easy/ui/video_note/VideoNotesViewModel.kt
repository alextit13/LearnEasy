package com.learn.easy.ui.video_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.db.DBGate
import com.learn.easy.utils.SingleEvent
import com.learn.easy.utils.VideoNote

class VideoNotesViewModel(private val app: Application) : AndroidViewModel(app) {

    val openAddScreen = MutableLiveData<SingleEvent<Boolean>>()

    fun onClickAdd() {
        openAddScreen.value = SingleEvent(true)
    }

    fun getNotes(): MutableList<VideoNote> {
        return DBGate.newInstance(app.applicationContext).getVideoNotes()
    }
}