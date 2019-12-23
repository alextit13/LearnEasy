package com.learn.easy.ui.canvas.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.db.DBGate
import com.learn.easy.utils.Paint
import com.learn.easy.utils.SingleEvent

class ListPaintsViewModel(val app: Application) : AndroidViewModel(app) {

    val listPaint = MutableLiveData<SingleEvent<MutableList<Paint>>>()

    fun onViewCreated() {
        listPaint.value = SingleEvent(DBGate.newInstance(app).getPaints())
    }
}