package com.example.pokemon.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.screens.navigator.AppScreensImpl
import com.github.terrakok.cicerone.Router

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val screens = AppScreensImpl()
    private val router = Router()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            router.navigateTo(screens.mainScreen())
        }
    }
}