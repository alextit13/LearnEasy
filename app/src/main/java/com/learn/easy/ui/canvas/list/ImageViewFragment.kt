package com.learn.easy.ui.canvas.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.learn.easy.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_view.*
import java.io.File

class ImageViewFragment : Fragment(R.layout.fragment_image_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pathToImage = arguments?.getString("urlImage") ?: ""
        Picasso.get().load(File(pathToImage)).into(ivImagePaint)
    }
}
