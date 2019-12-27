package com.learn.easy.ui.diary.open

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.db.DBGate
import com.learn.easy.utils.Diary

class OpenDiaryViewModel(private val app: Application) : AndroidViewModel(app) {

    val diaryLd = MutableLiveData<Diary>()

    fun viewWasCreated(idDiary: Int) {
        val diary = DBGate.newInstance(app.applicationContext)
            .getDiary(idDiary)

        diaryLd.value = diary
    }
}