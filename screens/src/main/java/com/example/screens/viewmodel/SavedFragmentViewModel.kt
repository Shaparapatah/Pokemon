package com.example.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.dto.CustomPokemonListItem
import com.example.repository.main.DefaultRepository
import com.example.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SavedFragmentViewModel(private val repository: DefaultRepository) :
    ViewModel() {

    private val _savedPokemon = MutableLiveData<Resource<List<CustomPokemonListItem>>>()
    val savedPokemon: LiveData<Resource<List<CustomPokemonListItem>>>
        get() = _savedPokemon


    fun getPokemonSavedPokemon() {
        _savedPokemon.postValue(Resource.Loading("loading"))
        viewModelScope.launch(Dispatchers.IO) {
            _savedPokemon.postValue(repository.getPokemonSavedPokemon())
        }
    }

    fun deletePokemon(customPokemonListItem: CustomPokemonListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePokemon(customPokemonListItem)
        }
    }


}