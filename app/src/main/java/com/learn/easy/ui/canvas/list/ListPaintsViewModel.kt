package com.learn.easy.ui.canvas.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.utils.Paint
import com.learn.easy.utils.SingleEvent

class ListPaintsViewModel(val app: Application) : AndroidViewModel(app) {

    val listPaint = MutableLiveData<SingleEvent<MutableList<Paint>>>()
    val toast = MutableLiveData<SingleEvent<String>>()

    fun onViewCreated() {
        listPaint.value = SingleEvent(DBGate.newInstance(app).getPaints())
    }

    fun onClickDelete(paint: Paint) {
        val deleted = DBGate.newInstance(app).deletePaint(paint.name)
        if (deleted) {
            toast.value = SingleEvent(app.getString(R.string.delete_success))
            listPaint.value = SingleEvent(DBGate.newInstance(app).getPaints())
        }
    }
}