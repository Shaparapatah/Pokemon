package com.example.repository.main

import com.example.model.dto.CustomPokemonListItem
import com.example.model.dto.PokemonDetailItem
import com.example.utils.Resource

interface DefaultRepository {
    //получение списка Покемонов
    suspend fun getPokemonList(): Resource<List<CustomPokemonListItem>>
    suspend fun getPokemonSavedPokemon(): Resource<List<CustomPokemonListItem>>
    suspend fun getPokemonDetail(id: String): Resource<PokemonDetailItem>
    suspend fun getLastStoredPokemon(): CustomPokemonListItem
    suspend fun searchPokemonByName(name: String): Resource<List<CustomPokemonListItem>>
    suspend fun searchPokemonByType(type: String): Resource<List<CustomPokemonListItem>>
    suspend fun savePokemon(pokemonListItem: CustomPokemonListItem)
}