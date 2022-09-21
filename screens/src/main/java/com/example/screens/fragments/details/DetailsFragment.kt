package com.example.screens.fragments.details

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.core.base.BaseFragment
import com.example.model.dto.CustomPokemonListItem
import com.example.model.dto.PokemonDetailItem
import com.example.screens.databinding.FragmentDetailsBinding
import com.example.screens.navigator.AppScreens
import com.example.screens.viewmodel.DetailsFragmentViewModel
import com.example.utils.ImageLoader
import com.example.utils.Resource
import com.github.terrakok.cicerone.Router
import java.util.*
import javax.inject.Inject

private const val TAG = "DetailsFragment"


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(
    FragmentDetailsBinding::inflate
) {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: AppScreens

    private val viewModel: DetailsFragmentViewModel by viewModels()
    lateinit var mPokemon: CustomPokemonListItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            it.getParcelable<CustomPokemonListItem>("pokemon")?.let { pokemon ->
                mPokemon = pokemon
                Log.d(TAG, pokemon.name)
                Log.d(TAG, pokemon.type.toString())
                pokemon.type?.let { it1 -> setType(it1) }
                Log.d(TAG, pokemon.id.toString())
                // setup name
                binding.detailFragmentTitleName.text = pokemon.name.capitalize()
                // query api for pokemon details
                getPokemonDetails(pokemon.apiId)
                subscribeObservers()
            }
        }

        if (this::mPokemon.isInitialized) {
            if (mPokemon.isSaved == "false") {
                binding.detailFragmentSaveButton.setOnClickListener {
                    mPokemon.isSaved = "true"
                    viewModel.savePokemon(mPokemon)
                    binding.detailFragmentSaveButton.text = "Saved"
                }
            } else {
                binding.detailFragmentSaveButton.text = "Saved"
            }
        }
    }

    private fun setType(type: String) {
        binding.detailFragmentType.text = "Type: ${type.capitalize(Locale.ROOT)}"
    }

    private fun subscribeObservers() {
        viewModel.pokemonDetails.observe(viewLifecycleOwner) { pokemondetails ->
            when (pokemondetails) {
                is Resource.Success -> {
                    pokemondetails.data?.let { pokemon ->
                        Log.d(TAG, pokemon.toString())
                        Log.d(TAG, pokemon.name.toString())
                        Log.d(TAG, pokemon.sprites.toString())
                        Log.d(TAG, pokemon.abilities.toString())
                        Log.d(TAG, pokemon.stat.toString())
                        Log.d(TAG, pokemon.types.toString())
                        setupView(pokemon)
                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, pokemondetails.message.toString())
                    Toast.makeText(
                        requireContext(),
                        "Ошибка, проверьте подключение к интернету",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Loading -> {
                    Log.d(TAG, pokemondetails.message.toString())
                }
                is Resource.Expired -> {
                    pokemondetails.data?.let { pokemon ->
                        setupView(pokemon)
                        Toast.makeText(
                            requireContext(),
                            "Ошибка получения данных, проверьте подключение к интернету",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
        viewModel.pokemonSaveIntent.observe(viewLifecycleOwner) { status ->
            if (status) {
                Toast.makeText(
                    requireContext(),
                    "Pokemon has been saved to your deck",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupView(pokemon: PokemonDetailItem) {
        pokemon.sprites.otherSprites.artwork.front_default?.let { image ->
            ImageLoader.loadImage(requireContext(), binding.detailFragmentImage, image)
        }


        // загрузка способностей Покемона

        for (i in pokemon.abilities) {
            val textView = TextView(requireContext())
            textView.text = i.ability.name.capitalize(Locale.ROOT)
            textView.textSize = 15f
            textView.setTextColor(Color.WHITE)
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.abilitiesContainer.addView(textView)


        }

        val pokemonstats = mutableListOf<Int>()


        // загруска статуса
        for (i in pokemon.stat) {
            val textView = TextView(requireContext())
            val progressBar = ProgressBar(requireContext(), null, R.attr.progressBarStyleHorizontal)
            progressBar.progress = i.baseStat ?: 0
            progressBar.progressTintList = ColorStateList.valueOf(Color.WHITE)
            textView.text = i.stat.name.capitalize(Locale.ROOT) + " ${i.baseStat}"
            textView.textSize = 15f
            textView.setTextColor(Color.WHITE)
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            i.baseStat?.let { pokemonstats.add(it) }
            binding.statsContainer.addView(textView)
            binding.statsContainer.addView(progressBar)


        }

        // отображение на карте
        mPokemon.Image?.let { ImageLoader.loadImage(requireContext(), binding.mapviewPlot, it) }

        // рандомная позиция
        ImageLoader.setMargins(
            binding.mapviewPlot,
            viewModel.plotLeft,
            viewModel.plotTop
        )
    }

    private fun getPokemonDetails(id: Int?) {
        viewModel.getPokemonDetails(id.toString())
    }

    companion object {
        fun newInstance(): DetailsFragment = DetailsFragment()
    }
}