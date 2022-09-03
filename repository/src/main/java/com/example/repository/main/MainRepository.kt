package com.example.repository.main

import android.util.Log
import com.example.model.dto.CustomPokemonListItem
import com.example.model.dto.PokemonDetailItem
import com.example.repository.api.RetrofitService
import com.example.repository.room.PokemonDAO
import com.example.utils.Constants
import com.example.utils.Resource
import javax.inject.Inject


private const val TAG = "MainRepository"

class MainRepository @Inject constructor(
    private val retrofit: RetrofitService,
    private val data: PokemonDAO
) : DefaultRepository {

    // used to confirm whether items where stored longer than 5 mins ago
    private val fiveMinutesAgo = System.currentTimeMillis() - Constants.CACHE


    override suspend fun getPokemonList(): Resource<List<CustomPokemonListItem>> {

        // check for results from DB
        val responseFromDB = data.getPokemon()
        if (responseFromDB.isNotEmpty()) {
            return Resource.Success(responseFromDB)
        } else {
            // if return null then preSeed from Constants
            val preSeedList = Constants.preSeedDB()

            // insert into DB
            data.insertPokemonList(preSeedList)

            // read from DB

            val initialDBRead = data.getPokemon()

            // return from DB
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


    // Single Source of Truth function
    // retrieve pokemon from DB if empty retrieve pokemon from api
    // if timestamp expired retrieve from api, insert results to db , updated timestamps and return items from db

    override suspend fun getPokemonDetail(id: String): Resource<PokemonDetailItem> {
        // first check DB for results
        val dbResult = data.getPokemonDetails(id)

        if (dbResult != null) {

            //if cache is older than 5 mins ago lets check the api for new results
            if (dbResult.timestamp?.toLong()!! < fiveMinutesAgo) {

                Log.d(TAG, "CACHE EXPIRED RETRIEVING NEW ITEM")

                // could throw exception if no internet available

                try {
                    val apiResult = retrofit.getPokemonDetail(id)
                    if (apiResult.isSuccessful) {

                        if (apiResult.body() != null) {

                            // add timestamp
                            val newPokemon = apiResult.body()
                            newPokemon!!.timestamp = System.currentTimeMillis().toString()

                            // store results in DB
                            data.insertPokemonDetailsItem(newPokemon)
                            // retrieve results from DB

                            val newDBRead = data.getPokemonDetails(id)

                            // return from DB

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
                // check if response is successful


            } else {
                // cache is sufficient let return it
                Log.d(TAG, "CACHE IS SUFFICIENT RETURN ITEM FROM DB")
                Log.d(TAG, "CACHED TIME : ${dbResult.timestamp} $fiveMinutesAgo")
                return Resource.Success(dbResult)
            }

        } else {
            // DB is empty so lets check the api
            // could throw exception if no internet available

            try {
                // check if response is successful
                val apiResult = retrofit.getPokemonDetail(id)
                if (apiResult.isSuccessful) {

                    if (apiResult.body() != null) {

                        // add timestamp
                        val newPokemon = apiResult.body()
                        newPokemon!!.timestamp = System.currentTimeMillis().toString()

                        // store results in DB
                        data.insertPokemonDetailsItem(newPokemon)
                        // retrieve results from DB

                        val newDBRead = data.getPokemonDetails(id)

                        // return from DB
                        Log.d(TAG, "NO ITEM IN DB FOUND, ITEM HAS BEEN RETRIEVED FROM API")
                        return Resource.Success(newDBRead!!)
                    } else {
                        return Resource.Error(apiResult.message())
                    }
                } else {
                    return Resource.Error(apiResult.message())
                }
            } catch (e: Exception) {
                return Resource.Error("error retrieving results")
            }


        }


    }


    // WorkManager

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
            return Resource.Error("no pokemon found")
        }
    }

    override suspend fun searchPokemonByType(type: String): Resource<List<CustomPokemonListItem>> {
        val dbResult = data.searchPokemonByType(type)

        return if (dbResult != null) {
            Resource.Success(dbResult)
        } else {
            Resource.Error("no pokemon found")
        }
    }

    override suspend fun savePokemon(pokemonListItem: CustomPokemonListItem) {
        data.insertPokemon(pokemonListItem)
    }

}