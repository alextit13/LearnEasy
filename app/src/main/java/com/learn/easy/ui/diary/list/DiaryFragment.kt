package com.learn.easy.ui.diary.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.learn.easy.R
import com.learn.easy.utils.Diary
import kotlinx.android.synthetic.main.fragment_diary.*

class DiaryFragment : Fragment(R.layout.fragment_diary) {

    private val viewModel: DiaryViewModel by lazy {
        ViewModelProviders.of(this)[DiaryViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initClickers()

        viewModel.viewWasInit()
    }

    private fun initObservers() {
        viewModel.apply {

            addDiary.observe(this@DiaryFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    openAddDiaryFragment(null)
                }
            })

            openDiary.observe(this@DiaryFragment, Observer {
                if (it.getContentIfNotHandled() != null) {
                    openDiaryFragment(it.content())
                }
            })

            toast.observe(this@DiaryFragment, Observer {
                if (it.getContentIfNotHandled() != null)
                    Toast.makeText(context, it.content(), Toast.LENGTH_LONG).show()
            })

            diariesLd.observe(this@DiaryFragment, Observer {
                initAdapter(it)
            })
        }
    }

    private fun openAddDiaryFragment(bundle: Bundle?) {
        findNavController().navigate(R.id.action_diaryFragment_to_addDiary, bundle)
    }

    private fun openDiaryFragment(id: Int) {
        findNavController().navigate(
            R.id.action_diaryFragment_to_openDiaryFragment,
            Bundle().apply {
                putInt("id", id)
            })
    }

    private fun initAdapter(diaries: MutableList<Diary>) {
        rvDiary.adapter = DiaryAdapter(diaries, {
            viewModel.onClickDiary(it)
        }, {
            showDeleteDialog(it)
        },
            {
                openAddDiaryFragment(Bundle().apply {
                    putInt("id_diary", it.id)
                })
            })
    }

    private fun showDeleteDialog(diary: Diary) {
        activity?.let { AlertDialog.Builder(it) }
            ?.setTitle(getString(R.string.delete))
            ?.setMessage(getString(R.string.delete_diary))
            ?.setPositiveButton(
                getString(R.string.yes)
            ) { p0, _ ->
                viewModel.onClickDelete(diary)
                p0.dismiss()
            }
            ?.setNegativeButton(getString(R.string.cancel)) { p0, _ ->
                p0.dismiss()
            }?.create()?.show()
    }

    private fun initClickers() {
        fabAddDiary.setOnClickListener { viewModel.onClickAddDiary() }
    }
}
