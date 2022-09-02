package com.example.pokemon.di

import com.example.pokemon.ui.main.MainActivity
import com.example.pokemon.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainScreen = module {
    scope(named<MainActivity>()) {
        viewModel {
            MainViewModel()
        }
    }
}