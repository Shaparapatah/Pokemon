package com.example.pokemon.ui.main.di.modules

import com.example.repository.api.RetrofitService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "BASE_URL"

@Module
class NetworkModule {

    @Provides
    @Named(BASE_URL)
    fun baseUrl(): String {
        return "https://pokeapi.co/"
    }

    @Singleton
    @Provides
    fun retrofitService(
        retrofit: Retrofit
    ): RetrofitService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun getRetrofit(
        @Named(BASE_URL) baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}