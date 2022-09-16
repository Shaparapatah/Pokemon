package com.example.pokemon.di

import androidx.room.Room
import com.example.pokemon.ui.main.MainViewModel
import com.example.repository.main.RepositoryImpl
import com.example.repository.room.PokemonDatabase
import com.example.screens.fragments.details.DetailsFragment
import com.example.screens.fragments.map.MapViewFragment
import com.example.screens.fragments.saved.SavedViewFragment
import com.example.screens.navigator.AppScreensImpl
import com.example.screens.viewmodel.DetailsFragmentViewModel
import com.example.screens.viewmodel.MainScreenViewModel
import com.example.screens.viewmodel.MapViewModel
import com.example.screens.viewmodel.SavedFragmentViewModel
import com.example.utils.Constants.CICERONE_NAME
import com.example.utils.Constants.MAIN_ACTIVITY_NAME
import com.example.utils.FragmentScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val screens = module {

    scope(named(MAIN_ACTIVITY_NAME)) {
        viewModel {
            MainViewModel()
        }
    }
    //Классы для навигации
    single<Cicerone<Router>>(named(CICERONE_NAME)) { Cicerone.create() }
    single<NavigatorHolder> {
        get<Cicerone<Router>>(named(CICERONE_NAME)).getNavigatorHolder()
    }
    single<Router> { get<Cicerone<Router>>(named(CICERONE_NAME)).router }
    single<AppScreensImpl> { AppScreensImpl() }

    //Классы для Scope фрагментов
    scope(named(FragmentScope.SHOW_MAIN_SCREEN_FRAGMENT_SCOPE)) {
        viewModel {
            MainScreenViewModel(get())
        }
    }
}

val database = module {
    single { Room.databaseBuilder(get(), PokemonDatabase::class.java, "PokemonDB").build() }
    single { get<PokemonDatabase>().pokemonDao() }
    single<RepositoryImpl> { RepositoryImpl(get(), get()) }
}

val detailsScreen = module {
    scope(named<DetailsFragment>()) {
        viewModel { DetailsFragmentViewModel(get()) }
    }
}
val savedScreen = module {
    scope(named<SavedViewFragment>()) {
        viewModel { SavedFragmentViewModel(get()) }
    }

}
val mapScreen = module {
    scope(named<MapViewFragment>()) {
        viewModel { MapViewModel(get()) }
    }
}