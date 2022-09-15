package com.example.screens.fragments.details

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.core.base.BaseFragment
import com.example.model.dto.CustomPokemonListItem
import com.example.model.dto.PokemonDetailItem
import com.example.screens.databinding.FragmentDetailsBinding
import com.example.screens.navigator.AppScreensImpl
import com.example.screens.viewmodel.DetailsFragmentViewModel
import com.example.utils.ImageLoader
import com.example.utils.Resource
import com.github.terrakok.cicerone.Router
import org.koin.java.KoinJavaComponent
import java.util.*

private const val TAG = "DetailsFragment"


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(
    FragmentDetailsBinding::inflate
) {

    //Навигация
    private val screens: AppScreensImpl = KoinJavaComponent.getKoin().get()
    private val router: Router = KoinJavaComponent.getKoin().get()

    //ViewModel
    lateinit var viewModel: DetailsFragmentViewModel

    lateinit var mPokemon: CustomPokemonListItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        // checking for details passed from list fragment
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
//                subscribeObservers()
            }
        }

        // setup saveButton clickListener if pokemon is not saved

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

    //Инициализцая ViewModel
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailsFragmentViewModel::class.java]
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
                        "Error retrieving results please check internet connection",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Loading -> {
                    Log.d(TAG, pokemondetails.message.toString())
                }
                is Resource.Expired -> {
                    pokemondetails.data?.let { pokemon ->
                        setupView(pokemon)

                        //inform user that cache has expired and we cannot retrieve up to date details
                        Toast.makeText(
                            requireContext(),
                            "Unable to retrieve up to date details !, please check network connectivity",
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

    //Setup Pokemon card info
    private fun setupView(pokemon: PokemonDetailItem) {
        // load image
        pokemon.sprites.otherSprites.artwork.front_default?.let { image ->
            ImageLoader.loadImage(requireContext(), binding.detailFragmentImage, image)
        }


        // load abilities

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


        // load stats

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

        // setup stars
        //if average of pokemon stats is less than 60 leave at one star if more than 60 but less than 80 then show 2 stars,more than 80 3 stars
        val pokemonAverage = pokemonstats.sum() / 6

        Log.d(TAG, "pokemon aveage is $pokemonAverage")

        // get dp value by checking screenSize
        val dp = (40 * (context?.resources?.displayMetrics?.density!!)).toInt()

        if (pokemonAverage in 61..79) {
            // add 1 star


            Log.d(TAG, "adding 1 star pokemon aveage is $pokemonAverage")
            val img = ImageView(requireContext())
            val lp = LinearLayout.LayoutParams(dp, dp) //make the image same size as first star
            img.layoutParams = lp
////            ImageLoader.loadImageDrawable(requireContext(), img, R.drawable.star)
//
//            binding.detailFragmentStarContainer.addView(img)

        }

        if (pokemonAverage > 79) {
            // add 2 stars


            Log.d(TAG, "adding 1 star pokemon aveage is $pokemonAverage")
            val img = ImageView(requireContext())
            val img2 = ImageView(requireContext())
            val lp = LinearLayout.LayoutParams(dp, dp) //make the image same size as first star
            img.layoutParams = lp
            img2.layoutParams = lp
//            ImageUtils.loadImageDrawable(requireContext(), img, R.drawable.star)
//            ImageUtils.loadImageDrawable(requireContext(), img2, R.drawable.star)

//            binding.detailFragmentStarContainer.addView(img)
//            binding.detailFragmentStarContainer.addView(img2)

        }

        // setup last location plot on map
        mPokemon.Image?.let { ImageLoader.loadImage(requireContext(), binding.mapviewPlot, it) }

        // set up random position
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