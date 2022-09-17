package com.example.pokemon.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.R
import com.example.pokemon.ui.main.app.PokemonApp
import com.example.screens.navigator.BackButtonListener
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private val navigator = AppNavigator(this, R.id.activity_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PokemonApp.instance.appComponent.inject(this)

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
    }
}