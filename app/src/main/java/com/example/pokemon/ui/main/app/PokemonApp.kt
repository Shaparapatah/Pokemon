package com.example.pokemon.ui.main.app

import AppModule
import android.app.Application
import com.example.pokemon.ui.main.di.components.AppComponent
import com.example.pokemon.ui.main.di.components.DaggerAppComponent


class PokemonApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    companion object {
        private var _instance: PokemonApp? = null
        val instance
            get() = _instance!!
    }

}