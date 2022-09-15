package com.example.pokemon.di

import com.example.pokemon.ui.main.MainViewModel
import com.example.screens.navigator.AppScreensImpl
import com.example.utils.Constants.CICERONE_NAME
import com.example.utils.Constants.MAIN_ACTIVITY_NAME
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val screens = module {
    //Scope для MainActivity
    scope(named(MAIN_ACTIVITY_NAME)) {
        viewModel {
            MainViewModel()
        }
    }
    single<Cicerone<Router>>(named(CICERONE_NAME)) { Cicerone.create() }
    single<NavigatorHolder> {
        get<Cicerone<Router>>(named(CICERONE_NAME)).getNavigatorHolder()
    }
    single<Router> { get<Cicerone<Router>>(named(CICERONE_NAME)).router }
    single<AppScreensImpl> { AppScreensImpl() }
}