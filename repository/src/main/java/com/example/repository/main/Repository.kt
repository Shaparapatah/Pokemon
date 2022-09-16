package com.example.repository.main

import com.example.model.dto.CustomPokemonListItem
import com.example.model.dto.PokemonDetailItem
import com.example.utils.Resource

interface Repository {
    //получение списка Покемонов
    suspend fun getPokemonList(): Resource<List<CustomPokemonListItem>>
    //Получение сохранённого Покемона
    suspend fun getPokemonSavedPokemon(): Resource<List<CustomPokemonListItem>>
    //Получение информации о Покемоне
    suspend fun getPokemonDetail(id: String): Resource<PokemonDetailItem>
    //Получение последнего сохранённого Покемона
    suspend fun getLastStoredPokemon(): CustomPokemonListItem
    //Поиск Покемона по имени
    suspend fun searchPokemonByName(name: String): Resource<List<CustomPokemonListItem>>
    //Поиск Покемона по типу
    suspend fun searchPokemonByType(type: String): Resource<List<CustomPokemonListItem>>
    //Сохранение Покемона
    suspend fun savePokemon(pokemonListItem: CustomPokemonListItem)
}