package com.learn.easy.ui

import com.learn.easy.ui.dialog.ChooseTypeTextDialogFragment
import com.learn.easy.ui.dialog.EnterTextDialog

abstract class BaseSelectFragment(layoutId: Int) : BaseFragment(layoutId) {

    abstract fun onClickSelect()
    abstract fun onClickEnter()
    abstract fun onEnterTextResult(text: String)

    protected fun showChooseDialog() {
        fragmentManager?.let {
            val d = ChooseTypeTextDialogFragment.newInstance()
            d.setTargetFragment(this, REQUEST_CODE_DIALOG)
            d.show(it, DIALOG_TAG)
        }
    }

    protected fun showEnterTextDialog() {
        fragmentManager?.let {
            val d = EnterTextDialog.newInstance()
            d.setTargetFragment(this, REQUEST_CODE_DIALOG_ENTER_TEXT)
            d.show(it, DIALOG_ENTER_TEXT_TAG)
        }
    }

    companion object {
        private const val DIALOG_TAG = "dialog_tag"
        private const val DIALOG_ENTER_TEXT_TAG = "dialog_enter_text_tag"
        private const val REQUEST_CODE_DIALOG = 331
        private const val REQUEST_CODE_DIALOG_ENTER_TEXT = 332
    }
}