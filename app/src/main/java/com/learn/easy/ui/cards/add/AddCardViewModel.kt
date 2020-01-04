package com.learn.easy.ui.cards.add

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.ui.cards.add.AddCardFragment.Companion.REQ_AUDIO
import com.learn.easy.ui.cards.add.AddCardFragment.Companion.REQ_IMAGE
import com.learn.easy.ui.cards.add.AddCardFragment.Companion.REQ_VIDEO
import com.learn.easy.utils.Card
import com.learn.easy.utils.PathCalculator
import com.learn.easy.utils.SingleEvent
import java.io.File
import java.util.*

class AddCardViewModel(private val app: Application) : AndroidViewModel(app) {

    private var videoPath: String = ""
    private var audioPath: String = ""
    private var imagePath: String = ""

    val toast = MutableLiveData<SingleEvent<String>>()

    val video = MutableLiveData<SingleEvent<Intent>>()
    val audio = MutableLiveData<SingleEvent<Intent>>()
    val image = MutableLiveData<SingleEvent<Intent>>()

    val cancel = MutableLiveData<SingleEvent<Boolean>>()

    private var imageName = ""

    fun onClickAddVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30)
        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES
            ).path + Date().time + ".mp4"
        )
        video.value = SingleEvent(intent)
    }

    fun onClickAddAudio() {
        val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
        audio.value = SingleEvent(intent)
    }

    fun onClickAddImage() {
        imageName = "${Date().time}.jpg"
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val uri  =Uri.fromFile(File(Environment.getExternalStorageDirectory().path, imageName))
        // val uri  = Uri.parse("file:///sdcard/photo$imageName")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        image.value = SingleEvent(intent)
    }

    fun onClickCancel() {
        cancel.value = SingleEvent(true)
    }

    fun onClickSave(title: String, description: String) {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            saveCard(title, description)
        } else {
            toast.value = SingleEvent(app.getString(R.string.enter_text_and_description))
        }
    }

    private fun saveCard(title: String, description: String) {
        val card = Card(
            Date().time.toString(),
            title,
            description,
            imagePath,
            audioPath,
            videoPath
        )
        DBGate.newInstance(app).insertCard(card)
        toast.value = SingleEvent(app.getString(R.string.save_success))
        cancel.value = SingleEvent(true)
    }

    fun onActivityResult(requestCode: Int, data: Intent?) {
        var mesage = ""
        when (requestCode) {
            REQ_VIDEO -> {
                mesage = app.getString(R.string.video_was_recorded)
                videoPath = PathCalculator.getPath(app.applicationContext, data?.data)
            }
            REQ_AUDIO -> {
                mesage = app.getString(R.string.audio_was_recorded)
                audioPath = PathCalculator.getPath(app.applicationContext, data?.data)
            }
            REQ_IMAGE -> {
                mesage = app.getString(R.string.image_take_success)
                imagePath = File(Environment.getExternalStorageDirectory().getPath(), imageName).absolutePath
            }
        }

        toast.value = SingleEvent(mesage)
    }
}