package com.learn.easy.ui.diary.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.utils.Diary
import com.learn.easy.utils.SingleEvent
import java.util.*
import kotlin.random.Random

class AddDiaryViewModel(private val app: Application) : AndroidViewModel(app) {

    val toast = MutableLiveData<SingleEvent<String>>()
    val exit = MutableLiveData<SingleEvent<Boolean>>()
    val diaryEdit = MutableLiveData<SingleEvent<Diary>>()

    private var isEdit = false
    private var diary: Diary? = null

    fun onClickSaveButton(title: String, text: String) {

        if (title.isEmpty()) {
            toast.value = SingleEvent(app.getString(R.string.enter_title))
            return
        }

        if (text.isEmpty()) {
            toast.value = SingleEvent(app.getString(R.string.enter_text))
            return
        }

        if (!isEdit) {
            diary = Diary(
                Random.nextInt(),
                Date().time.toString(),
                title,
                text
            )
            DBGate.newInstance(app.applicationContext).insertDiary(diary!!)
        } else {
            diary?.apply {
                this.title = title
                this.date = Date().time.toString()
                this.text = text
            }?.let { DBGate.newInstance(app.applicationContext).updateDiary(it) }
        }


        toast.value = SingleEvent(app.getString(R.string.diary_created))
        exit.value = SingleEvent(true)
        isEdit = false
    }

    fun openForEdit(id: Int) {
        isEdit = true
        diary = DBGate.newInstance(app).getDiary(id)
        if (diary != null) {
            diaryEdit.value = SingleEvent(diary!!)
        }
    }
}