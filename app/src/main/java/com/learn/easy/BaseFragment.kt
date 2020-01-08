package com.learn.easy

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

open class BaseFragment(layoutId: Int): Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModelProviders.of(this).get(BaseViewModel::class.java)
            .toast.observe(this, Observer {
            if (it.getContentIfNotHandled() != null) {
                Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
            }
        })
    }
}