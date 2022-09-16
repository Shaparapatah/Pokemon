package com.example.repository.api

import com.example.model.dto.PokemonDetailItem
import com.example.model.dto.PokemonListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("api/v2/pokemon")
    suspend fun getPokemonList(): Response<PokemonListItem>

    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): Response<PokemonDetailItem>
}