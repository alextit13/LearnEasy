package com.learn.easy.ui.canvas.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.learn.easy.R
import com.learn.easy.utils.Paint
import kotlinx.android.synthetic.main.fragment_list_paints.*

class ListPaintsFragment : Fragment(R.layout.fragment_list_paints) {

    private val viewModel: ListPaintsViewModel by lazy {
        ViewModelProviders.of(this)[ListPaintsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onViewCreated()

        observers()
    }

    private fun observers() {
        viewModel.apply {
            listPaint.observe(this@ListPaintsFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    initAdapter(it.content())
                }
            })
            toast.observe(this@ListPaintsFragment, Observer {
                if (it.getContentIfNotHandled() != null)
                    Toast.makeText(activity, it.content(), Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun initAdapter(list: MutableList<Paint>) {
        rvPaints.adapter = PaintAdapter(list, {
            findNavController().navigate(R.id.action_listPaintsFragment_to_imageViewFragment,
                Bundle().apply {
                    putString("urlImage", it.imagePath)
                })
        }, {
            showDeleteDialog(it)
        })
    }

    private fun showDeleteDialog(paint: Paint) {
        activity?.let { AlertDialog.Builder(it) }
            ?.setTitle(getString(R.string.delete))
            ?.setMessage(getString(R.string.delete_paint))
            ?.setPositiveButton(
                getString(R.string.yes)
            ) { p0, p1 ->
                viewModel.onClickDelete(paint)
                p0.dismiss()
            }
            ?.setNegativeButton(getString(R.string.cancel)) { p0, p1 ->
                p0.dismiss()
            }?.create()?.show()
    }
}
