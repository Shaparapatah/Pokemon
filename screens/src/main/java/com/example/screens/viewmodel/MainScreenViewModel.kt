package com.example.screens.viewmodel

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

class MainScreenViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    private val _pokemonList = MutableLiveData<Resource<List<CustomPokemonListItem>>>()
    val pokemonList: LiveData<Resource<List<CustomPokemonListItem>>>
        get() = _pokemonList

    fun getPokemonList() {
        _pokemonList.postValue(Resource.Loading(""))
        viewModelScope.launch {
            _pokemonList.postValue(repository.getPokemonList())
        }
    }

    fun searchPokemonByName(name: String) {
        _pokemonList.postValue(Resource.Loading("loading"))
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonList.postValue(repository.searchPokemonByName(name))
        }
    }

    fun searchPokemonByType(type: String) {
        _pokemonList.postValue(Resource.Loading("loading"))
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonList.postValue(repository.searchPokemonByType(type))
        }
    }
}