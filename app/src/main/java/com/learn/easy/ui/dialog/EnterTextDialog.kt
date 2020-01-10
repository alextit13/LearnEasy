package com.learn.easy.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.learn.easy.R
import com.learn.easy.ui.BaseSelectFragment
import kotlinx.android.synthetic.main.dialog_enter_text.*

class EnterTextDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.dialog_enter_text, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCancelEnterText.setOnClickListener { dismiss() }
        btnSaveEnterText.setOnClickListener {
            (targetFragment as? BaseSelectFragment)?.onEnterTextResult(
                etEnterText.text.toString()
            )
            dismiss()
        }
    }

    companion object {
        fun newInstance() = EnterTextDialog()
    }
}