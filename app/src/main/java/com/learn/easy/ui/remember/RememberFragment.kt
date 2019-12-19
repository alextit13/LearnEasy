package com.learn.easy.ui.remember

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import com.learn.easy.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_remember.*

class RememberFragment : BaseFragment(R.layout.fragment_remember) {

    private val viewModel: RememberViewModel by lazy {
        ViewModelProviders.of(this).get(RememberViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        callbacks()

        viewModel.makeSpannableString("История с выходным 31 декабря закрутилась еще в конце октября. Тогда появилась петиция о том, чтобы сделать выходными не только 1 и 2 января, но также 31 декабря, и не отрабатывать эти дни в субботы. Петицию подписали более 15 тысяч человек. В начале ноября премьер-министр Сергей Румас пообещал рассмотреть это предложение, а 6 декабря рассказал, к какому решению пришли чиновники. Вот и все")
    }

    private fun initListeners() {
        ibShowWordMode.setOnClickListener { viewModel.onClickShowWordMode() }
        ibShowAllMode.setOnClickListener { viewModel.onClickShowAllMode() }
        ibRefreshAll.setOnClickListener { viewModel.onClickRefreshAll() }
        fabSelectDoc.setOnClickListener { viewModel.onClickSelectDoc() }
        ivPreviousPage.setOnClickListener { viewModel.onClickPreviousPage() }
        ivNextPage.setOnClickListener { viewModel.onClickNextPage() }
        tvRemember.movementMethod = ScrollingMovementMethod()
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