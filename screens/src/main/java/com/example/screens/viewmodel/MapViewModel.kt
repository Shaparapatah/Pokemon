package com.example.screens.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.dto.CustomPokemonListItem
import com.example.repository.main.Repository
import com.example.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MapViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _pokemonList = MutableLiveData<Resource<List<CustomPokemonListItem>>>()
    val pokemonList: LiveData<Resource<List<CustomPokemonListItem>>>
        get() = _pokemonList


    fun getPokemonList() {
        _pokemonList.postValue(Resource.Loading("loading"))
        Log.d("ViewModel", "function called")
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonList.postValue(repository.getPokemonList())
        }
    }

}