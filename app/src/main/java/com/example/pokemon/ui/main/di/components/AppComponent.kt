package com.example.pokemon.ui.main.di.components

import com.example.pokemon.ui.main.MainActivity
import com.example.pokemon.ui.main.di.modules.CiceroneModule
import com.example.pokemon.ui.main.di.modules.DBModules
import com.example.pokemon.ui.main.di.modules.NetworkModule
import com.example.pokemon.ui.main.di.modules.RepositoryModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        DBModules::class,
        CiceroneModule::class,
        NetworkModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}