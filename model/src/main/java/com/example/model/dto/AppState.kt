package com.example.model.dto

sealed class AppState {

    data class Success(val data: List<PokemonListItem>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}