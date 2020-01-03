package com.learn.easy.ui.video_note

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.learn.easy.R
import com.learn.easy.utils.VideoNote
import kotlinx.android.synthetic.main.item_video_note.view.*
import java.util.*

class VideoNoteAdapter(
    var notes: MutableList<VideoNote>,
    private val callback: (VideoNote) -> Unit,
    private val longClick: (VideoNote) -> Unit
) : RecyclerView.Adapter<VideoNoteAdapter.Holder>() {

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_video_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val n = notes[position]
        holder.apply {
            icon.setBackgroundColor(getRandomColors())
            firstChar.text = n.title.first().toLowerCase().toString()
            title.text = n.title
            subtitle.text = n.subtitle

            itemView.setOnClickListener { callback.invoke(n) }
            itemView.setOnLongClickListener {
                longClick.invoke(n)
                true
            }
        }
    }

    private fun getRandomColors(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }

    class Holder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val icon: CardView = rootView.cvVideoNoteIcon
        val firstChar: TextView = rootView.tvFirstChar
        val title: TextView = rootView.tvTitle
        val subtitle: TextView = rootView.tvText
    }
}