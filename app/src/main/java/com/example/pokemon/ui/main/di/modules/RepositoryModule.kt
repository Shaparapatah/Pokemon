package com.example.pokemon.ui.main.di.modules

import com.example.repository.api.RetrofitService
import com.example.repository.main.Repository
import com.example.repository.main.RepositoryImpl
import com.example.repository.room.PokemonDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(api: RetrofitService, dao: PokemonDAO): Repository =
        RepositoryImpl(api, dao)
}