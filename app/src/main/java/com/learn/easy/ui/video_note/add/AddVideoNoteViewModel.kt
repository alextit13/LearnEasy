package com.learn.easy.ui.video_note.add

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.utils.PathCalculator
import com.learn.easy.utils.SingleEvent
import com.learn.easy.utils.VideoNote
import java.util.*

class AddVideoNoteViewModel(private val app: Application) : AndroidViewModel(app) {

    private var videoPath = ""
    val toast = MutableLiveData<SingleEvent<String>>()
    val startVideo = MutableLiveData<SingleEvent<Intent>>()
    val close = MutableLiveData<SingleEvent<Boolean>>()

    fun onClickSave(title: String) {
        if (title.isNotEmpty() && videoPath.isNotEmpty()) {
            save(title)
        } else {
            toast.value = SingleEvent(app.getString(R.string.enter_data))
        }
    }

    private fun save(title: String) {
        val note = VideoNote(
            Date().time.toString(),
            title,
            "",
            title.first().toLowerCase().toString(),
            videoPath
        )
        DBGate.newInstance(app).insertVideoNote(note)
        toast.value = SingleEvent(app.getString(R.string.save_success))
        close.value = SingleEvent(true)
    }

    fun onClickRecordVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30)
        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES
            ).path + Date().time + ".mp4"
        )
        startVideo.value = SingleEvent(intent)
    }

    fun onVideoWasRecord(uri: Uri) {
        videoPath = PathCalculator.getPath(app.applicationContext, uri)
        toast.value = SingleEvent(app.getString(R.string.video_was_recorded))
    }
}