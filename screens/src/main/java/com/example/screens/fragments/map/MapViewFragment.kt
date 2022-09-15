package com.example.screens.fragments.map

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.core.base.BaseFragment
import com.example.screens.databinding.FragmentMapBinding
import com.example.screens.navigator.AppScreensImpl
import com.example.screens.viewmodel.MapViewModel
import com.example.utils.ImageLoader
import com.example.utils.Resource
import com.github.terrakok.cicerone.Router
import org.koin.java.KoinJavaComponent


private const val TAG = "MapViewFragment"


class MapViewFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate) {


    //Навигация
    private val screens: AppScreensImpl = KoinJavaComponent.getKoin().get()
    private val router: Router = KoinJavaComponent.getKoin().get()

//    //ViewModel
//    lateinit var viewModel: MapViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initViewModel()

        //setup click listener on custom backButton

        binding.mapFragmentBack.setOnClickListener {
            router.navigateTo(screens.mainScreen())
        }

        // retrieve pokemon
//        initObservers()

//        lifecycleScope.launchWhenStarted {
//            viewModel.getPokemonList()
//        }
    }

    //Инициализцая ViewModel
//    fun initViewModel() {
//        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
//    }


//    private fun initObservers() {
//        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
//            when (list) {
//                is Resource.Success -> {
//                    if (list.data != null) {
//                        if (list.data!!.isNotEmpty()) {
//                            Log.d(TAG, list.data.toString())
//                            showProgressbar(false)
//
//                            for (i in list.data!!) {
//                                Log.d(TAG, i.name)
//                                val img = ImageView(requireContext())
//                                val lp = RelativeLayout.LayoutParams(
//                                    200,
//                                    200
//                                ) //make the image a bit bigger in this fragment
//                                img.layoutParams = lp
//
//                                // setup last location plot
//                                i.Image?.let { it1 ->
//                                    ImageLoader.loadImage(
//                                        requireContext(),
//                                        img,
//                                        it1
//                                    )
//                                }
//
//                                // set up random position
//                                i.positionLeft?.let { left ->
//                                    i.positionTop?.let { right ->
//                                        ImageLoader.setMargins(
//                                            img,
//                                            left,
//                                            right,
//                                        )
//                                    }
//                                }
//
//                                // add img to map
//
//
//                                binding.mapFragmentImgLayout.addView(img)
//                            }
//
//                        } else {
//                            // setup empty recyclerview
//                            showProgressbar(false)
//                            Toast.makeText(
//                                requireContext(),
//                                "no pokemon found !",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//
//                }
//                is Resource.Error -> {
//                    Log.d(TAG, list.message.toString())
//                    showProgressbar(false)
//                    Toast.makeText(requireContext(), "no pokemon found !", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                is Resource.Loading -> {
//                    showProgressbar(true)
//                }
//            }
//        }
//    }

    private fun showProgressbar(isVisible: Boolean) {
        binding.mapFragmentProgress.isVisible = isVisible
    }

    companion object {
        fun newInstance(): MapViewFragment = MapViewFragment()
    }

}