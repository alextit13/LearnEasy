package com.learn.easy.ui.remember

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bachors.wordtospan.WordToSpan
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import com.learn.easy.utils.WordFinder.findWordForRightHanded
import kotlinx.android.synthetic.main.fragment_remember.*

class RememberFragment : BaseFragment(R.layout.fragment_remember) {

    private val viewModel: RememberViewModel by lazy {
        ViewModelProviders.of(this).get(RememberViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        callbacks()

        val s = "История с выходным 31 декабря закрутилась еще в конце октября. Тогда появилась петиция о том, чтобы сделать выходными не только 1 и 2 января, но также 31 декабря, и не отрабатывать эти дни в субботы. Петицию подписали более 15 тысяч человек. В начале ноября премьер-министр Сергей Румас пообещал рассмотреть это предложение, а 6 декабря рассказал, к какому решению пришли чиновники. Вот и все"
        // viewModel.makeSpannableString(s)
        tvRemember.text = s
    }

    private fun initListeners() {
        ibShowWordMode.setOnClickListener { viewModel.onClickShowWordMode() }
        ibShowAllMode.setOnClickListener { viewModel.onClickShowAllMode() }
        ibRefreshAll.setOnClickListener { viewModel.onClickRefreshAll() }
        fabSelectDoc.setOnClickListener { viewModel.onClickSelectDoc() }
        ivPreviousPage.setOnClickListener { viewModel.onClickPreviousPage() }
        ivNextPage.setOnClickListener { viewModel.onClickNextPage() }
        // tvRemember.movementMethod = LinkMovementMethod.getInstance()

        tvRemember.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                val mOffset = tvRemember.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                //  mTxtOffset.setText("" + mOffset);
                val sts = findWordForRightHanded(tvRemember.text.toString(), mOffset)
                println("sdl;kfskd")
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
                ibShowWordMode.setBackgroundResource(if (it) R.drawable.bg_round_button_pressed else R.drawable.bg_round_button)
            })
        }
    }
}