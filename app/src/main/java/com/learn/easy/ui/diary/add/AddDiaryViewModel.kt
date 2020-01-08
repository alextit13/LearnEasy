package com.learn.easy.ui.diary.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.BaseViewModel
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.utils.Diary
import com.learn.easy.utils.SingleEvent
import java.util.*

class AddDiaryViewModel(app: Application) : BaseViewModel(app) {

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
                Date().time.toString(),
                title,
                text
            )
            DBGate.newInstance(app.applicationContext).insertDiary(diary!!)
        } else {
            diary?.apply {
                this.title = title
                this.text = text
            }?.let {
                val result = DBGate.newInstance(app.applicationContext).updateDiary(it)
                if (result < 1) {
                    showToast(app.getString(R.string.error))
                }
            }
        }


        toast.value = SingleEvent(app.getString(R.string.diary_created))
        exit.value = SingleEvent(true)
        isEdit = false
    }

    fun openForEdit(date: String) {
        isEdit = true
        diary = DBGate.newInstance(app).getDiary(date)
        if (diary != null) {
            diaryEdit.value = SingleEvent(diary!!)
        }
    }
}