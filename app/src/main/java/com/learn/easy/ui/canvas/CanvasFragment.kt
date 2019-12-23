package com.learn.easy.ui.canvas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_canvas.*
import java.io.File

class CanvasFragment : BaseFragment(R.layout.fragment_canvas), ISavePaintCallback {

    private var colorDefaultPosition = 0
    private var widthDefaultPosition = 0

    private val viewModel: CanvasViewModel by lazy {
        ViewModelProviders.of(this)[CanvasViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        observers()
    }

    private fun observers() {
        viewModel.apply {
            clear.observe(
                this@CanvasFragment,
                Observer { if (it.getContentIfNotHandled() != null) clear() })
            colorPicker.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) showColorPicker()
            })
            colorPosition.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    colorDefaultPosition = it.content()
                }
            })
            colorSelect.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    setColor(it.content())
                }
            })

            widthPicker.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    showWidthPicker()
                }
            })
            widthPosition.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    widthDefaultPosition = it.content()
                }
            })
            widthSelect.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    setWidth(it.content())
                }
            })

            toast.observe(this@CanvasFragment, Observer {
                Toast.makeText(activity, it.content(), Toast.LENGTH_LONG).show()
            })

            savePaint.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    savePaint(it.content())
                }
            })

            listPaints.observe(this@CanvasFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    openListPaints()
                }
            })
        }
    }

    private fun openListPaints() {
        findNavController().navigate(R.id.action_nav_canvas_to_listPaintsFragment)
    }

    private fun showWidthPicker() {
        val alertDialogBuilder = activity?.let { AlertDialog.Builder(it) }
        val colors = arrayOf(
            getString(R.string.w_1),
            getString(R.string.w_1_1),
            getString(R.string.w_1_2),
            getString(R.string.w_1_3),
            getString(R.string.w_1_4),
            getString(R.string.w_1_5),
            getString(R.string.w_1_6),
            getString(R.string.w_1_7),
            getString(R.string.w_1_8),
            getString(R.string.w_1_9),
            getString(R.string.w_2)
        )
        alertDialogBuilder?.setSingleChoiceItems(
            colors, widthDefaultPosition
        ) { a, b ->
            viewModel.widthWasChooses(b)
            a.dismiss()
        }?.setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }?.setTitle(getString(R.string.lbl_width))
            ?.create()?.show()
    }

    private fun showColorPicker() {
        val alertDialogBuilder = activity?.let { AlertDialog.Builder(it) }
        val colors = arrayOf(
            getString(R.string.black),
            getString(R.string.red),
            getString(R.string.green),
            getString(R.string.blue),
            getString(R.string.white),
            getString(R.string.yellow)
        )
        alertDialogBuilder?.setSingleChoiceItems(
            colors, colorDefaultPosition
        ) { a, b ->
            viewModel.colorWasChooses(b)
            a.dismiss()
        }?.setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }?.setTitle(getString(R.string.color))
            ?.create()?.show()
    }

    private fun initListeners() {
        btnSaveCanvas.setOnClickListener { viewModel.onClickBtnSave(etTitleCanvas.text.toString()) }
        llColor.setOnClickListener { viewModel.onClickColor() }
        llWidth.setOnClickListener { viewModel.onClickWidth() }
        llClear.setOnClickListener { viewModel.onClickClear() }
        ibListPaints.setOnClickListener { viewModel.onClickListPaints() }
    }

    private fun savePaint(name: String){
        drawingView.savePaint(name, this)
    }

    override fun saveSuccess(fileImage: File, nameImage: String) {
        activity?.runOnUiThread {
            viewModel.saveImageWasSuccess(fileImage, nameImage)
            Toast.makeText(activity, getString(R.string.save_success), Toast.LENGTH_LONG).show()
        }
    }

    private fun clear() {
        drawingView.clear()
    }

    private fun setWidth(width: Float) {
        drawingView.setWidth(width)
    }

    private fun setColor(color: Int) {
        drawingView.setColor(color)
    }
}