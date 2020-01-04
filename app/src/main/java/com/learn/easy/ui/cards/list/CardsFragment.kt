package com.learn.easy.ui.cards.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.learn.easy.R
import com.learn.easy.ui.cards.show.ShowCardFragment.Companion.TAG_CARD
import com.learn.easy.utils.Card
import kotlinx.android.synthetic.main.fragment_cards.*

class CardsFragment : Fragment(R.layout.fragment_cards) {

    private var adapter: CardAdapter? = null
    private val viewModel: CardsViewModel by lazy {
        ViewModelProviders.of(this).get(CardsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.apply {
            notes.observe(this@CardsFragment, Observer {
                refreshAdapter(it)
            })
            openAdd.observe(this@CardsFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    openAddCard()
                }
            })
            showDeleteDialog.observe(this@CardsFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    showDeleteDialog(it.content())
                }
            })
            toast.observe(this@CardsFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
                }
            })
            openCard.observe(this@CardsFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    openCardItem(it.content())
                }
            })
            viewWasInit()
        }
    }

    private fun openCardItem(card: Card) {
        findNavController().navigate(R.id.action_nav_cards_to_showCardFragment,
            Bundle().apply {
                putSerializable(TAG_CARD, card)
            })
    }

    private fun showDeleteDialog(card: Card) {
        activity?.let { AlertDialog.Builder(it) }
            ?.setTitle(getString(R.string.delete))
            ?.setMessage(getString(R.string.delete_card))
            ?.setPositiveButton(
                getString(R.string.yes)
            ) { p0, _ ->
                viewModel.onClickDelete(card)
                p0.dismiss()
            }
            ?.setNegativeButton(getString(R.string.cancel)) { p0, _ ->
                p0.dismiss()
            }?.create()?.show()
    }

    private fun openAddCard() {
        findNavController().navigate(R.id.action_nav_cards_to_addCardFragment)
    }

    private fun initListeners() {
        fabAddCard.setOnClickListener { viewModel.onClickAdd() }
    }

    private fun refreshAdapter(notes: MutableList<Card>) {
        if (adapter == null) {
            adapter = CardAdapter(notes, {
                viewModel.onClickCard(it)
            }, {
                viewModel.onLongClickCard(it)
            })
        } else {
            adapter?.items = notes
        }
        rvCards.adapter = adapter
    }
}