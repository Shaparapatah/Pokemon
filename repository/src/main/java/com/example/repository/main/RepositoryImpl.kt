package com.example.repository.main

import android.util.Log
import com.example.model.dto.CustomPokemonListItem
import com.example.model.dto.PokemonDetailItem
import com.example.repository.api.RetrofitService
import com.example.repository.room.PokemonDAO
import com.example.utils.Constants
import com.example.utils.Resource


private const val TAG = "MainRepository"

class RepositoryImpl(
    private val retrofit: RetrofitService,
    private val data: PokemonDAO
) : Repository {

    //Если данные храняться более 5 минут
    private val fiveMinutesAgo = System.currentTimeMillis() - Constants.CACHE


    override suspend fun getPokemonList(): Resource<List<CustomPokemonListItem>> {


        val responseFromDB = data.getPokemon()
        if (responseFromDB.isNotEmpty()) {
            return Resource.Success(responseFromDB)
        } else {

            val preSeedList = Constants.preSeedDB()

            // запись в ДБ
            data.insertPokemonList(preSeedList)

            // чтение с ДБ
            val initialDBRead = data.getPokemon()

            // возврат с ДБ
            return Resource.Success(initialDBRead)

        }


    }

    override suspend fun getPokemonSavedPokemon(): Resource<List<CustomPokemonListItem>> {
        val dbResult = data.getSavedPokemon()
        if (dbResult.isNullOrEmpty()) {
            return Resource.Error("saved pokemon list is empty")
        } else {
            return Resource.Success(dbResult)
        }
    }

    override suspend fun getPokemonDetail(id: String): Resource<PokemonDetailItem> {
        // first check DB for results
        val dbResult = data.getPokemonDetails(id)

        if (dbResult != null) {
            //Если кэш хранится больше 5 минут, делает API запрос
            if (dbResult.timestamp?.toLong()!! < fiveMinutesAgo) {

                Log.d(TAG, "CACHE EXPIRED RETRIEVING NEW ITEM")

                try {
                    val apiResult = retrofit.getPokemonDetail(id)
                    if (apiResult.isSuccessful) {

                        if (apiResult.body() != null) {
                            val newPokemon = apiResult.body()
                            newPokemon!!.timestamp = System.currentTimeMillis().toString()
                            data.insertPokemonDetailsItem(newPokemon)
                            val newDBRead = data.getPokemonDetails(id)
                            return Resource.Success(newDBRead!!)
                        } else {
                            // return expired object to let user know cache has expired and we cannot find new items from Api
                            return Resource.Expired(
                                "Cache expired and cannot retrieve new Pokemon please check network connectivity ",
                                dbResult
                            )
                        }
                    } else {
                        return Resource.Error(apiResult.message())
                    }
                } catch (e: Exception) {
                    return Resource.Error("error retrieving results")
                }


            } else {
                Log.d(TAG, "CACHE IS SUFFICIENT RETURN ITEM FROM DB")
                Log.d(TAG, "CACHED TIME : ${dbResult.timestamp} $fiveMinutesAgo")
                return Resource.Success(dbResult)
            }

        } else {
            try {
                val apiResult = retrofit.getPokemonDetail(id)
                if (apiResult.isSuccessful) {
                    if (apiResult.body() != null) {
                        val newPokemon = apiResult.body()
                        newPokemon!!.timestamp = System.currentTimeMillis().toString()
                        data.insertPokemonDetailsItem(newPokemon)
                        val newDBRead = data.getPokemonDetails(id)
                        Log.d(TAG, "NO ITEM IN DB FOUND, ITEM HAS BEEN RETRIEVED FROM API")
                        return Resource.Success(newDBRead!!)
                    } else {
                        return Resource.Error(apiResult.message())
                    }
                } else {
                    return Resource.Error(apiResult.message())
                }
            } catch (e: Exception) {
                return Resource.Error("Ошибка")
            }
        }
    }


    override suspend fun getLastStoredPokemon(): CustomPokemonListItem {
        return data.getLastStoredPokemonObject()
    }

    override suspend fun searchPokemonByName(name: String): Resource<List<CustomPokemonListItem>> {
        val dbResult = data.searchPokemonByName(name)

        if (dbResult != null) {
            Log.d(TAG, dbResult.toString())
            return Resource.Success(dbResult)

        } else {
            Log.d(TAG, dbResult.toString())
            return Resource.Error("Покемон не найден")
        }
    }

    override suspend fun searchPokemonByType(type: String): Resource<List<CustomPokemonListItem>> {
        val dbResult = data.searchPokemonByType(type)

        return if (dbResult != null) {
            Resource.Success(dbResult)
        } else {
            Resource.Error("Покемон не найден")
        }
    }

    override suspend fun savePokemon(pokemonListItem: CustomPokemonListItem) {
        data.insertPokemon(pokemonListItem)
    }

}