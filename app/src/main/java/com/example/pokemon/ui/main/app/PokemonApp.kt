package com.example.pokemon.ui.main.app

import android.app.Application
import com.example.pokemon.ui.main.di.components.AppComponent
import com.example.pokemon.ui.main.di.components.DaggerAppComponent


class PokemonApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
//            .appModule(AppModule(this))
            .build()
    }

    companion object {
        private var _instance: PokemonApp? = null
        val instance
            get() = _instance
    }
}