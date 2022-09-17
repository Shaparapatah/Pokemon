package com.example.screens.fragments.saved

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.core.base.BaseFragment
import com.example.model.dto.CustomPokemonListItem
import com.example.screens.R
import com.example.screens.adapter.SavedPokemonAdapter
import com.example.screens.databinding.FragmentSavedPokemonBinding
import com.example.screens.viewmodel.SavedFragmentViewModel
import com.example.utils.Resource

private const val TAG = "SavedViewFragment"

class SavedViewFragment :
    BaseFragment<FragmentSavedPokemonBinding>(FragmentSavedPokemonBinding::inflate) {

    private val viewModel: SavedFragmentViewModel by viewModels()
    private lateinit var pokemonSavedListAdapter: SavedPokemonAdapter

    private var count = 0
    private var savedList =
        mutableListOf<CustomPokemonListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.savedFragmentBack.setOnClickListener {
//            router.navigateTo(screens.mainScreen())
        }

        //setup settings icon
        binding.deleteFragment.setOnClickListener {
//            deleteAllPokemon()
        }


        lifecycleScope.launchWhenStarted {
            setupRv()
            initObservers()
            viewModel.getPokemonSavedPokemon()
        }
    }

    private fun setupRv() {
        pokemonSavedListAdapter = SavedPokemonAdapter()
        pokemonSavedListAdapter.setOnClickListener(object :
            SavedPokemonAdapter.OnClickListener {
            override fun onClick(item: CustomPokemonListItem) {
                // create bundle to pass to next fragment
                val bundle = Bundle()
                bundle.putParcelable("pokemon", item)
                findNavController().navigate(
                    R.id.action_savedViewFragment_to_detailFragment,
                    bundle
//                router.navigateTo(screens.detailsScreen())
                )
            }

        })

        pokemonSavedListAdapter.setOnDeleteListener(object :
            SavedPokemonAdapter.OnDeleteListener {

            override fun onDelete(item: CustomPokemonListItem, pos: Int) {
                deletePokemon(item, pos)
            }
        })
        binding.rvSavedFragment.apply {
            adapter = pokemonSavedListAdapter
        }
    }

    private fun initObservers() {
        viewModel.savedPokemon.observe(viewLifecycleOwner) { savedPokemon ->
            when (savedPokemon) {
                is Resource.Success -> {
                    savedPokemon.data?.let {
                        if (savedPokemon.data!!.isNotEmpty()) {

                            count = savedPokemon.data!!.size
                            savedList = savedPokemon.data as MutableList<CustomPokemonListItem>
                            pokemonSavedListAdapter.setList(savedPokemon.data as MutableList<CustomPokemonListItem>)
                            binding.rvSavedFragment.invalidate()
                            pokemonSavedListAdapter.notifyDataSetChanged()

                        }
                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, savedPokemon.message.toString())
                }
                is Resource.Loading -> {
                    Log.d(TAG, "LOADING")
                }
            }

        }
    }

    private fun deletePokemon(customPokemonListItem: CustomPokemonListItem, pos: Int) {

        val builder =
            AlertDialog.Builder(requireContext()) // using custom theme
        builder.setMessage("Are you sure you want to delete this Pokemon ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                customPokemonListItem.isSaved = "false"
                pokemonSavedListAdapter.removeItemAtPosition(pos)
                pokemonSavedListAdapter.notifyDataSetChanged() // update RV
                count -= 1 // update count
                Log.d(TAG, count.toString())
                if (count == 0) {
                }
                viewModel.deletePokemon(customPokemonListItem)
            }
            .setNegativeButton("No") { dialog, id ->

                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    companion object {
        fun newInstance(): SavedViewFragment = SavedViewFragment()
    }
}