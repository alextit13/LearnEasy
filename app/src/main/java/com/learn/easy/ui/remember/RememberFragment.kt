package com.learn.easy.ui.remember

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import com.learn.easy.utils.clickableSpan
import kotlinx.android.synthetic.main.fragment_remember.*

class RememberFragment : BaseFragment(R.layout.fragment_remember) {

    private val viewModel: RememberViewModel by lazy {
        ViewModelProviders.of(this).get(RememberViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        callbacks()

        Toast.makeText(context, getString(R.string.select_doc), Toast.LENGTH_LONG).show()
    }

    private fun initListeners() {
        ibShowWordMode.setOnClickListener { viewModel.onClickShowWordMode() }
        ibShowAllMode.setOnClickListener { viewModel.onClickShowAllMode() }
        ibRefreshAll.setOnClickListener { viewModel.onClickRefreshAll() }
        fabSelectDoc.setOnClickListener { viewModel.onClickSelectDoc() }
        ivPreviousPage.setOnClickListener { viewModel.onClickPreviousPage() }
        ivNextPage.setOnClickListener { viewModel.onClickNextPage() }

        tvRemember.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN
                && viewModel.isWordSuggestionEnable()
            ) {
                if (!tvRemember.text.isEmpty()) {
                    clickableSpan(tvRemember, motionEvent)
                }
            }
            false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun callbacks() {
        viewModel.apply {
            chooserLiveData.observe(this@RememberFragment, Observer { it ->
                if (it.getContentIfNotHandled() != null) {
                    selectFile {
                        viewModel.onFilesWasChoose(it)
                    }
                }
            })

            textViewModel.observe(this@RememberFragment, Observer {
                tvRemember.text = it
            })

            currentPageViewModel.observe(this@RememberFragment, Observer {
                tvPages.text = "${it.first} / ${it.second}"
            })

            showWordSuggestionMode.observe(this@RememberFragment, Observer {
                ibShowWordMode.setBackgroundResource(
                    if (it.content())
                        R.drawable.bg_round_button_pressed
                    else
                        R.drawable.bg_round_button
                )
            })
        }
    }
}