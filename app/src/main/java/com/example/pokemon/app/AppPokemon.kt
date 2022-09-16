package com.example.pokemon.app

import android.app.Application
import com.example.pokemon.di.database
import com.example.pokemon.di.screens
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class AppPokemon : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        //Koin
        startKoin {
            androidContext(applicationContext)
            modules(listOf(screens, database))
        }
    }

    companion object {
        var instance: AppPokemon? = null
    }
}