package com.example.screens.fragments.mainscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.base.BaseFragment
import com.example.model.dto.CustomPokemonListItem
import com.example.screens.R
import com.example.screens.adapter.MainScreenAdapter
import com.example.screens.databinding.FragmentMainScreenBinding
import com.example.screens.dialogs.FilterDialog
import com.example.screens.viewmodel.MainScreenViewModel
import com.example.utils.Resource

private const val TAG = "MainScreenFragment"


class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>(
    FragmentMainScreenBinding::inflate
), FilterDialog.TypePicker {

//    val mainActivity: MainActivity by lazy {
//        requireActivity() as MainActivity
//    }

    private val viewModel: MainScreenViewModel by viewModels()
    private lateinit var adapterPokemon: MainScreenAdapter
    private var pokemonList = mutableListOf<CustomPokemonListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        initRv()
        initClickListeners()
        initSearchView()
        initObservers()
        initButtons()

        lifecycleScope.launchWhenStarted {
            viewModel.getPokemonList()
        }
    }

    //Инициализация кнопок
    private fun initButtons() {
        binding.mainScreenFragmentMapFAB.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_mapViewFragment)

        }
        binding.mainScreenFragmentSavedFAB.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_savedViewFragment)
        }
    }

    private fun showEmptyRecyclerViewError() {
        Toast.makeText(requireContext(), "no items found", Toast.LENGTH_SHORT).show()
    }

    private fun initObservers() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            when (list) {
                is Resource.Success -> {
                    Log.d(TAG, list.data.toString())
                    if (list.data != null) {
                        if (list.data!!.isNotEmpty()) {
                            Log.d(TAG, list.data.toString())
                            pokemonList = list.data as ArrayList<CustomPokemonListItem>
                            adapterPokemon.setList(list.data as ArrayList<CustomPokemonListItem>)
                            binding.rvMainScreenFragment.invalidate()
                            adapterPokemon.notifyDataSetChanged()
                        } else {
                            showEmptyRecyclerViewError()
                        }
                    } else {
                        showEmptyRecyclerViewError()
                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, list.message.toString())
                    showEmptyRecyclerViewError()

                }
                is Resource.Loading -> {
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.mainScreenFilterImg.setOnClickListener {
            val dialog = FilterDialog(this)
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(dialog, "dialog")
            transaction.commit()
        }
    }


    private fun initSearchView() {
        binding.mainScreenSearchView.setOnClickListener {
            if (binding.mainScreenSearchView.query.isEmpty()) {
                viewModel.getPokemonList()
            }

        }
        binding.mainScreenSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchPokemonByName(query)
                } else {
                    viewModel.getPokemonList()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.length > 2) {
                        Log.d(TAG, newText)
                        viewModel.searchPokemonByName(newText)
                    }
                }
                return false
            }
        })
    }

    private fun initRv() {
        adapterPokemon = MainScreenAdapter()

        adapterPokemon.setOnClickListener(object : MainScreenAdapter.OnClickListener {
            override fun onClick(item: CustomPokemonListItem) {
                val bundle = Bundle()
                bundle.putParcelable("pokemon", item)
                findNavController().navigate(
                    R.id.action_savedViewFragment_to_detailFragment,
                    bundle
                )
            }

        })
        binding.rvMainScreenFragment.apply {
            adapter = adapterPokemon
        }
    }

    override fun typeToSearch(type: String) {
        Log.d(TAG, type)
        viewModel.searchPokemonByType(type)
    }

    companion object {
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }

}