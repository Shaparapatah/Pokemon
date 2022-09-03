package com.example.screens.fragments.details

import android.os.Bundle
import android.view.View
import com.example.core.base.BaseFragment
import com.example.screens.databinding.FragmentDetailsBinding

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(
    FragmentDetailsBinding::inflate
) {

    private lateinit var viewModel: DetailsFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): DetailsFragment = DetailsFragment()
    }
}