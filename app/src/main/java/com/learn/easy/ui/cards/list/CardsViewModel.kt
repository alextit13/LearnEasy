package com.learn.easy.ui.cards.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.learn.easy.R
import com.learn.easy.db.DBGate
import com.learn.easy.utils.Card
import com.learn.easy.utils.SingleEvent

class CardsViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    val notes = MutableLiveData<MutableList<Card>>()

    val openAdd = MutableLiveData<SingleEvent<Boolean>>()
    val toast = MutableLiveData<SingleEvent<String>>()
    val showDeleteDialog = MutableLiveData<SingleEvent<Card>>()

    val openCard = MutableLiveData<SingleEvent<Card>>()

    fun viewWasInit() {
        getAllNotes()
    }

    private fun getAllNotes() {
        notes.value = DBGate.newInstance(app.applicationContext).getAllCards()
    }

    fun onClickCard(card: Card) {
        openCard.value = SingleEvent(card)
    }

    fun onLongClickCard(card: Card) {
        showDeleteDialog.value = SingleEvent(card)
    }

    fun onClickAdd() {
        openAdd.value = SingleEvent(true)
    }

    fun onClickDelete(card: Card) {
        DBGate.newInstance(app).deleteCard(card)
        toast.value = SingleEvent(app.getString(R.string.delete_success))
    }
}