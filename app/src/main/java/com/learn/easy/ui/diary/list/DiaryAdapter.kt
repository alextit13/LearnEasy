package com.learn.easy.ui.diary.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.learn.easy.R
import com.learn.easy.utils.Diary
import kotlinx.android.synthetic.main.item_diary.view.*
import java.text.SimpleDateFormat
import java.util.*

class DiaryAdapter(
    private val diaries: MutableList<Diary>,
    private val callback: (Diary) -> Unit,
    private val longClick: (Diary) -> Unit,
    private val editClick: (Diary) -> Unit
) : RecyclerView.Adapter<DiaryAdapter.DiaryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryHolder =
        DiaryHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_diary, parent, false)
        )

    override fun getItemCount() = diaries.size

    override fun onBindViewHolder(holder: DiaryHolder, position: Int) {
        val diary = diaries[position]
        holder.apply {
            charItem.text = diary.title.first().toString().toUpperCase(Locale.ROOT)
            title.text = diary.title
            text.text = diary.text
            icon.setCardBackgroundColor(getRandomColors())
            date.text =
                SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(Date(diary.date.toLong()))

            itemView.setOnClickListener { callback.invoke(diary) }
            itemView.setOnLongClickListener {
                longClick.invoke(diary)
                true
            }
            edit.setOnClickListener { editClick.invoke(diary) }
        }
    }

    private fun getRandomColors(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    class DiaryHolder(
        rootView: View
    ) : RecyclerView.ViewHolder(rootView) {
        val charItem: TextView = rootView.tvFirstChar
        val title: TextView = rootView.tvTitle
        val text: TextView = rootView.tvText
        val icon: CardView = rootView.cvDiaryIcon
        val date: TextView = rootView.tvDate
        val edit: ImageView = rootView.ivEdit
    }
}