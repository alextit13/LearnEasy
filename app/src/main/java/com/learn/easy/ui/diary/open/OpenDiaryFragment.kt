package com.learn.easy.ui.diary.open

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.utils.Diary
import kotlinx.android.synthetic.main.fragment_open_diary.*
import java.text.SimpleDateFormat
import java.util.*

class OpenDiaryFragment : Fragment(R.layout.fragment_open_diary) {

    private val viewModel: OpenDiaryViewModel by lazy {
        ViewModelProviders.of(this)[OpenDiaryViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDiaryIdFromBundle()
        observables()
    }

    private fun getDiaryIdFromBundle() {
        val date = arguments?.getString("date_diary") ?: ""
        viewModel.viewWasCreated(date)
    }

    private fun observables() {
        viewModel.diaryLd.observe(this, Observer {
            if (it != null)
                setDataInViews(it)
        })
    }

    private fun setDataInViews(diary: Diary) {
        tvTitle.text = diary.title
        tvDate.text = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(diary.date.toLong())
        tvText.apply {
            text = diary.text
            movementMethod = ScrollingMovementMethod()
        }
    }
}
