package com.example.pokemon.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Подключение Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
//            router.navigateTo(screens.mainScreen())
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