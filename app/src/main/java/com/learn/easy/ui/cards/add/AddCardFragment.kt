package com.learn.easy.ui.cards.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.learn.easy.R
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : Fragment(R.layout.fragment_add_card) {

    private val viewModel: AddCardViewModel by lazy {
        ViewModelProviders.of(this).get(AddCardViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        viewModel.apply {
            toast.observe(this@AddCardFragment, Observer {
                Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
            })
        }
    }
}
