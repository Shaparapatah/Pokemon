package com.example.screens.navigator

import com.example.screens.fragments.details.DetailsFragment
import com.example.screens.fragments.mainscreen.MainScreenFragment
import com.example.screens.fragments.map.MapViewFragment
import com.example.screens.fragments.saved.SavedViewFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AppScreensImpl : AppScreens {
    override fun mainScreen() = FragmentScreen {
        MainScreenFragment.newInstance()
    }

    override fun detailsScreen() = FragmentScreen {
        DetailsFragment.newInstance()
    }

    override fun savedScreen() = FragmentScreen {
        SavedViewFragment.newInstance()
    }

    override fun mapViewScreen() = FragmentScreen {
        MapViewFragment.newInstance()
    }

}