package com.example.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.dto.CustomPokemonListItem
import com.example.model.dto.PokemonDetailItem
import com.example.repository.main.Repository
import com.example.utils.Resource
import com.example.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailsFragmentViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    // single live event to stop stale items being stored in Live Data
    private val _pokemonDetails = SingleLiveEvent<Resource<PokemonDetailItem>>()
    val pokemonDetails: LiveData<Resource<PokemonDetailItem>>
        get() = _pokemonDetails


    private val _pokemonSaveIntent = MutableLiveData<Boolean>()
    val pokemonSaveIntent: LiveData<Boolean>
        get() = _pokemonSaveIntent

    // value for map plotting, held in view model to ensure the same location is plotted after rotation

    val plotLeft = (0..600).random()
    val plotTop = (0..600).random()


    fun getPokemonDetails(id: String) {
        _pokemonDetails.postValue(Resource.Loading("loading"))
        viewModelScope.launch(Dispatchers.IO) {
            _pokemonDetails.postValue(repository.getPokemonDetail(id))
        }
    }


    fun savePokemon(customPokemonListItem: CustomPokemonListItem) {
        _pokemonSaveIntent.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePokemon(customPokemonListItem)
        }
    }


}