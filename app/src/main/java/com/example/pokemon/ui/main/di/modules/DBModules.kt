package com.example.pokemon.ui.main.di.modules

import android.content.Context
import androidx.room.Room
import com.example.repository.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

private const val DB_NAME = "database.db"

@Module
class DBModules {

    @Singleton
    @Provides
    fun pokemonDAO(context: Context): PokemonDatabase = Room
        .databaseBuilder(context, PokemonDatabase::class.java, DB_NAME)
        .build()
}