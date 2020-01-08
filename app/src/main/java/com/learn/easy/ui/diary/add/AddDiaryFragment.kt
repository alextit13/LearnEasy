package com.learn.easy.ui.diary.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.utils.Diary
import kotlinx.android.synthetic.main.fragment_add_diary.*

class AddDiaryFragment : Fragment(R.layout.fragment_add_diary) {

    private val viewModel: AddDiaryViewModel by lazy {
        ViewModelProviders.of(this)[AddDiaryViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickers()
        observers()

        checkEditOrCreate()
    }

    private fun checkEditOrCreate() {
        val date = arguments?.getString("date_diary")
        if (date != null) {
            // edit
            viewModel.openForEdit(date)
        }
    }

    private fun initClickers() {
        btnSave.setOnClickListener {
            viewModel.onClickSaveButton(
                etTitle.text.toString(),
                etText.text.toString()
            )
        }
    }

    private fun observers() {
        viewModel.apply {
            toast.observe(this@AddDiaryFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
                }
            })
            exit.observe(this@AddDiaryFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    activity?.onBackPressed()
                }
            })
            diaryEdit.observe(this@AddDiaryFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    setTextInViews(it.content())
                }
            })
        }
    }

    private fun setTextInViews(diary: Diary) {
        etTitle.setText(diary.title)
        etText.setText(diary.text)
    }
}
