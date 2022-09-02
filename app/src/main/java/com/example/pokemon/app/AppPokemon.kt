package com.example.pokemon.app

import android.app.Application
import com.example.pokemon.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppPokemon: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(mainScreen)
        }
    }
}