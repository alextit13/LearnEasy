package com.learn.easy.ui.video_note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.utils.SingleEvent
import com.learn.easy.utils.VideoNote

class VideoNotesViewModel(private val app: Application) : AndroidViewModel(app) {

    val list = MutableLiveData<MutableList<VideoNote>>()
    val openAddScreen = MutableLiveData<SingleEvent<Boolean>>()
    val showDialog = MutableLiveData<SingleEvent<VideoNote>>()
    val showToast = MutableLiveData<SingleEvent<String>>()

    fun onClickAdd() {
        openAddScreen.value = SingleEvent(true)
    }

    fun viewWasInit() {
        list.value = DBGate.newInstance(app.applicationContext).getVideoNotes()
    }

    fun getNotes(): MutableList<VideoNote> {
        return DBGate.newInstance(app.applicationContext).getVideoNotes()
    }

    fun onLongClick(note: VideoNote) {
        showDialog.value = SingleEvent(note)
    }

    fun onClickDelete(note: VideoNote) {
        DBGate.newInstance(app).deleteVideoNote(note)
        showToast.value = SingleEvent(app.getString(R.string.delete_success))
        viewWasInit()
    }
}