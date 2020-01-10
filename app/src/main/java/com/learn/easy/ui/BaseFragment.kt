package com.learn.easy.ui

import androidx.fragment.app.Fragment
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog

abstract class BaseFragment(val layout: Int) : Fragment(layout) {

    protected fun selectFile(callback: (Array<String>) -> Unit) {
        val dialog = FilePickerDialog(activity, DialogProperties().apply {
            selection_type = DialogConfigs.FILE_SELECT
            selection_mode = DialogConfigs.SINGLE_MODE
        })
        dialog.setDialogSelectionListener {
            if (it.isNotEmpty()) callback.invoke(it)
        }
        dialog.show()
    }
}