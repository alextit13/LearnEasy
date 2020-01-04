package com.learn.easy.ui.cards.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.easy.R
import com.learn.easy.utils.Card
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card.view.*
import java.io.File

class CardAdapter(
    var items: MutableList<Card>,
    private val callback: (Card) -> Unit,
    private val longCallback: (Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val c = items[position]
        holder.apply {

            title.text = c.title
            val imagePath = c.imagePath
            if (imagePath.isNotEmpty()) {
                Picasso.get().load(File(imagePath)).into(image)
            }

            itemView.setOnClickListener { callback.invoke(c) }
            itemView.setOnLongClickListener {
                longCallback.invoke(c)
                true
            }
        }
    }

    class Holder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val title: TextView = rootView.tvCardTitle
        val image: ImageView = rootView.ivCardImage
    }
}