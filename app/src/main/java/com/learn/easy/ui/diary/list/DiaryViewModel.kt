package com.learn.easy.ui.diary.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.utils.Diary
import com.learn.easy.utils.SingleEvent

class DiaryViewModel(
    val app: Application
) : AndroidViewModel(app) {

    val toast = MutableLiveData<SingleEvent<String>>()
    val diariesLd = MutableLiveData<MutableList<Diary>>()
    val openDiary = MutableLiveData<SingleEvent<Int>>()
    val addDiary = MutableLiveData<SingleEvent<Boolean>>()

    fun viewWasInit() {
        getAllDiaries()
    }

    private fun getAllDiaries() {
        val diaries = DBGate.newInstance(app.applicationContext)
            .getAllDiaries()

        if (diaries.isNotEmpty()) {
            diariesLd.value = diaries
        } else {
            toast.value = SingleEvent(app.getString(R.string.not_have_diaries))
        }
    }

    fun onClickAddDiary() {
        addDiary.value = SingleEvent(true)
    }

    fun onClickDiary(diary: Diary) {
        openDiary.value = SingleEvent(diary.id)
    }

    fun onClickDelete(diary: Diary) {
        if (DBGate.newInstance(app).deleteDiary(diary.id))
            toast.value = SingleEvent(app.getString(R.string.delete_success))
        else
            toast.value = SingleEvent(app.getString(R.string.error))
        getAllDiaries()
    }
}