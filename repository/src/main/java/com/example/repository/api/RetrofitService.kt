package com.example.repository.api

import com.example.model.dto.PokemonDetailItem
import retrofit2.Response
import retrofit2.http.Path

interface RetrofitService {

    suspend fun getPokemonDetail(
        @Path("id") id: String
    ): Response<PokemonDetailItem>
}