package com.example.screens.navigator

import com.example.screens.fragments.details.DetailsFragment
import com.example.screens.fragments.mainscreen.MainScreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AppScreensImpl : AppScreens {
    override fun mainScreen() = FragmentScreen {
        MainScreenFragment.newInstance()
    }

    override fun detailsScreen() = FragmentScreen {
        DetailsFragment.newInstance()
    }
}