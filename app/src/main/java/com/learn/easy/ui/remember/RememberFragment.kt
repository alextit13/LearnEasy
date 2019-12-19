package com.learn.easy.ui.remember

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog
import com.learn.easy.R
import kotlinx.android.synthetic.main.fragment_remember.*

class RememberFragment : Fragment() {

    private lateinit var viewModel: RememberViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(RememberViewModel::class.java)
        return inflater.inflate(R.layout.fragment_remember, container, false)
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
            chooserLiveData.observe(this@RememberFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    selectFile()
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

    private fun selectFile() {
        val dialog = FilePickerDialog(activity, DialogProperties().apply {
            selection_type = DialogConfigs.FILE_SELECT
            selection_mode = DialogConfigs.SINGLE_MODE
        })
        dialog.setDialogSelectionListener {
            if (it.isNotEmpty()) viewModel.onFilesWasChoose(it)
        }
        dialog.show()
    }
}