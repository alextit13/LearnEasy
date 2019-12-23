package com.learn.easy.ui.canvas.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.easy.R
import com.learn.easy.utils.Paint
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_paint.view.*
import java.io.File

class PaintAdapter(
    private val list: MutableList<Paint>,
    private val callback: (Paint) -> Unit,
    private val longCallback:(Paint) -> Unit
) : RecyclerView.Adapter<PaintAdapter.PaintViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaintViewHolder =
        PaintViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_paint, parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PaintViewHolder, position: Int) {
        list[position].apply {
            holder.name.text = name
            Picasso.get().load(File(imagePath)).into(holder.image)
            holder.itemView.setOnClickListener {
                callback.invoke(this)
            }
            holder.itemView.setOnLongClickListener {
                longCallback.invoke(this)
                true
            }
        }
    }

    class PaintViewHolder(
        rootView: View
    ) : RecyclerView.ViewHolder(rootView) {
        val name: TextView = rootView.tvPaintName
        val image: ImageView = rootView.ivIconPaint
    }
}