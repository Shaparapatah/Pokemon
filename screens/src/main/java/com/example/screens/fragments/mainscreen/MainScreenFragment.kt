package com.example.screens.fragments.mainscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.core.base.BaseFragment
import com.example.model.dto.CustomPokemonListItem
import com.example.screens.adapter.MainScreenAdapter
import com.example.screens.databinding.FragmentMainScreenBinding
import com.example.screens.dialogs.FilterDialog
import com.example.screens.navigator.AppScreensImpl
import com.example.screens.viewmodel.MainScreenViewModel
import com.example.utils.Resource
import com.github.terrakok.cicerone.Router
import org.koin.java.KoinJavaComponent

private const val TAG = "MainScreenFragment"


class MainScreenFragment : BaseFragment<FragmentMainScreenBinding>(
    FragmentMainScreenBinding::inflate
), FilterDialog.TypePicker {

    //Навигация
    private val screens: AppScreensImpl = KoinJavaComponent.getKoin().get()
    private val router: Router = KoinJavaComponent.getKoin().get()

    //ViewModel
//    private lateinit var viewModel: MainScreenViewModel

    //Adapter
    lateinit var pokemonListAdapter: MainScreenAdapter


    private var pokemonList = mutableListOf<CustomPokemonListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        initRv()
        initClickListeners()
//        initSearchView()
//        setupAlert()
//        setupFabButtons()
//        initObservers()
//        initViewModel()
        initButtons()
//        initObservers()

//
//        lifecycleScope.launchWhenStarted {
//            viewModel.getPokemonList()
//        }

    }

    //Инициализация кнопок
    private fun initButtons() {
        binding.mainScreenFragmentMapFAB.setOnClickListener {

//            findNavController().navigate(R.id.action_listFragment_to_mapViewFragment)
            router.navigateTo(screens.mapViewScreen())

        }
        binding.mainScreenFragmentSavedFAB.setOnClickListener {
            router.navigateTo(screens.savedScreen())
//            findNavController().navigate(R.id.action_listFragment_to_savedViewFragment)
        }


    }

    //Инициализцая ViewModel
//    private fun initViewModel() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.getPokemonList()
//        }
//    }

//    private fun setupFabButtons() {
////        binding.listFragmentMapFAB.setOnClickListener {
////
////            findNavController().navigate(R.id.action_listFragment_to_mapViewFragment)
////
////
////        }
////        binding.listFragmentSavedFAB.setOnClickListener {
////            findNavController().navigate(R.id.action_listFragment_to_savedViewFragment)
////        }
////    }

    private fun initClickListeners() {
        binding.mainScreenFilterImg.setOnClickListener {
            val dialog = FilterDialog(this)
            val transaction = childFragmentManager.beginTransaction()
            transaction.add(dialog, "dialog")
            transaction.commit()
        }
    }

//
//    private fun initSearchView() {
//        binding.mainScreenSearchView.setOnClickListener {
//            if (binding.mainScreenSearchView.query.isEmpty()) {
//                viewModel.getPokemonList()
//            }

//        }
//        binding.mainScreenSearchView.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener {
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query != null) {
//                    viewModel.searchPokemonByName(query)
//                } else {
//                    viewModel.getPokemonList()
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText != null) {
//                    // search DB after two letters are typed
//                    if (newText.length > 2) {
//                        Log.d(TAG, newText)
//                        viewModel.searchPokemonByName(newText)
//                    }
//                }
//                return false
//            }
//
//        })
//    }
//
//    private fun setupAlert() {
//        val workerStatusPref =
//            context?.getSharedPreferences("worker", AppCompatActivity.MODE_PRIVATE)
//
//        val workerStatus = workerStatusPref?.getString("worker", "")
//
//        if (workerStatus!! == "cancel") {
//            val builder = AlertDialog.Builder(requireContext()) // using custom theme
//            builder.setMessage("Re-enable background searching?")
//                .setCancelable(false)
//                .setPositiveButton("Yes") { dialog, id ->
//
//                    // disable work manager tasks
//                    val pref =
//                        context?.getSharedPreferences("worker", AppCompatActivity.MODE_PRIVATE)
//                    val editor = pref?.edit()
//
//                    editor?.let {
//                        editor.putString("worker", "enabled")
//                        editor.commit()
//                    }
//                    Toast.makeText(requireContext(), "Background tasks enabled", Toast.LENGTH_SHORT)
//                        .show()
//
//                }
//                .setNegativeButton("No") { dialog, id ->
//                    // Dismiss the dialog
//                    dialog.dismiss()
//                }
//            val alert = builder.create()
//            alert.show()
//        } else {
//            val builder = AlertDialog.Builder(requireContext()) // using custom theme
//            builder.setMessage("Disable background pokemon search tasks ?")
//                .setCancelable(false)
//                .setPositiveButton("Yes") { dialog, id ->
//
//                    // disable work manager tasks
//                    val pref =
//                        context?.getSharedPreferences("worker", AppCompatActivity.MODE_PRIVATE)
//                    val editor = pref?.edit()
//
//                    editor?.let {
//                        editor.putString("worker", "cancel")
//                        editor.commit()
//                    }
//                    Toast.makeText(
//                        requireContext(),
//                        "Background tasks cancelled",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                }
//                .setNegativeButton("No") { dialog, id ->
//                    // Dismiss the dialog
//                    dialog.dismiss()
//                }
//            val alert = builder.create()
//            alert.show()
//        }
//    }
//
//    private fun initRv() {
//        pokemonListAdapter = MainScreenAdapter()
//
//        // setup on click for RecyclerView Items
//
//        pokemonListAdapter.setOnClickListener(object : MainScreenAdapter.OnClickListener {
//            override fun onClick(item: CustomPokemonListItem) {
//                router.navigateTo(screens.detailsScreen())
////
////                val bundle = Bundle()
////                bundle.putParcelable("pokemon", item)
////                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
//            }
//
//        })
//
//        binding.rvMainScreenFragment.apply {
//            adapter = pokemonListAdapter
//        }
//
//    }


//    // intialise Observers for liveData objects in ViewModel
//    private fun initObservers() {
//        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { list ->
//            when (list) {
//                is Resource.Success -> {
//                    Log.d(TAG, list.data.toString())
//                    if (list.data != null) {
//                        if (list.data!!.isNotEmpty()) {
//                            Log.d(TAG, list.data.toString())
//                            pokemonList = list.data as ArrayList<CustomPokemonListItem>
//                            pokemonListAdapter.setList(list.data as ArrayList<CustomPokemonListItem>)
//                            binding.rvMainScreenFragment.invalidate()
//                            pokemonListAdapter.notifyDataSetChanged()
//                            //  showProgressbar(false)
//
//                            // swipe to refresh layout is used then lets stop the refresh animation here
////                            if (binding.listFragmentSwipeToRefresh.isRefreshing) {
////                                binding.listFragmentSwipeToRefresh.isRefreshing = false
////                            }
//                        } else {
//                            // setup empty recyclerview
//                            //    showProgressbar(false)
//                            showEmptyRecyclerViewError()
//
//                        }
//                    } else {
//                        showEmptyRecyclerViewError()
//                    }
//
//                }
//                is com.example.utils.Resource.Error -> {
//                    Log.d(TAG, list.message.toString())
//                    //  showProgressbar(false)
//                    // setup empty recyclerview
//                    showEmptyRecyclerViewError()
//
//                }
//                is com.example.utils.Resource.Loading -> {
//                    //   showProgressbar(true)
//                }
//            }
//        })
//    }

    private fun showEmptyRecyclerViewError() {
        Toast.makeText(requireContext(), "no items found", Toast.LENGTH_SHORT).show()
    }


//    private fun showProgressbar(isVisible: Boolean) {
//        binding.listFragmentProgress.isVisible = isVisible
//    }

// get type chosen by user in dialog
//
//    override fun typeToSearch(type: String) {
//        Log.d(TAG, type)
//        viewModel.searchPokemonByType(type)
//    }

    companion object {
        fun newInstance(): MainScreenFragment = MainScreenFragment()
    }

    override fun typeToSearch(type: String) {
        TODO("Not yet implemented")
    }

}