package com.example.screens.fragments.mainscreen

import android.os.Bundle
import android.view.View
import com.example.core.base.BaseFragment
import com.example.screens.databinding.FragmentMainScreenBinding
import java.lang.IllegalStateException

class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>(
    FragmentMainScreenBinding::inflate
) {

    private lateinit var viewModel: MainScreenViewModel
    private val adapter: MainScreenAdapter by lazy { MainScreenAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        if (binding.recyclerView.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
    }

    private fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }
}