package com.learn.easy.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.learn.easy.R
import com.learn.easy.ui.BaseSelectFragment
import kotlinx.android.synthetic.main.dialog_chooser.*

class ChooseTypeTextDialogFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.dialog_chooser, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSelect.setOnClickListener {
            (targetFragment as? BaseSelectFragment)?.onClickSelect()
            dismiss()
        }

        btnEnter.setOnClickListener {
            (targetFragment as? BaseSelectFragment)?.onClickEnter()
            dismiss()
        }
    }

    companion object {
        fun newInstance() = ChooseTypeTextDialogFragment()
    }
}