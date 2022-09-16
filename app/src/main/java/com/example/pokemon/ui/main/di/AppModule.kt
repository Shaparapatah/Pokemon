package com.example.pokemon.ui.main.di

import android.content.Context
import com.example.repository.api.RetrofitService
import com.example.repository.main.Repository
import com.example.repository.main.RepositoryImpl
import com.example.repository.room.PokemonDAO
import com.example.repository.room.PokemonDatabase
import com.example.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Hilt Module Class for providing dependencies to be used in repository, viewmodels and workmanager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokeApi(): RetrofitService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)

    @Provides
    fun providePokemonDao(@ApplicationContext appContext: Context): PokemonDAO {
        return PokemonDatabase.getDatabase(appContext).pokemonDao()
    }


    @Singleton
    @Provides
    fun provideMainRepository(api: RetrofitService, dao: PokemonDAO): Repository =
        RepositoryImpl(api, dao)


}