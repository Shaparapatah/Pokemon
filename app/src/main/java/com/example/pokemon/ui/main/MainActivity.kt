package com.example.pokemon.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.base.AppScreens
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navigator = AppNavigator(this@MainActivity, R.id.activity_container)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    lateinit var screens: AppScreens
    lateinit var router: Router

    //Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Подключение Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            navigatorHolder.setNavigator(navigator)
            router.replaceScreen(screens.mainScreen())
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
    }
}