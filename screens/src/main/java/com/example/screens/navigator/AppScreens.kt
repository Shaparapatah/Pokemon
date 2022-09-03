package com.example.screens.navigator

import com.github.terrakok.cicerone.androidx.FragmentScreen

interface AppScreens {
    fun mainScreen(): FragmentScreen
    fun detailsScreen(): FragmentScreen
}