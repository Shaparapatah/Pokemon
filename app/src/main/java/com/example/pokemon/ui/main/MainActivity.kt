package com.example.pokemon.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.screens.navigator.AppScreensImpl
import com.example.screens.navigator.BackButtonListener
import com.example.utils.Constants.MAIN_ACTIVITY_NAME
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent


class MainActivity : AppCompatActivity() {

    //Навигация
    private val navigator = AppNavigator(this@MainActivity, R.id.activity_container)
    private val screens: AppScreensImpl = KoinJavaComponent.getKoin().get()
    private val router: Router = KoinJavaComponent.getKoin().get()
    private val navigatorHolder: NavigatorHolder = KoinJavaComponent.getKoin().get()

    //ViewModel
    private val mainActivityScope: Scope =
        KoinJavaComponent.getKoin().getOrCreateScope(
            MAIN_ACTIVITY_NAME, named(MAIN_ACTIVITY_NAME)
        )
    lateinit var model: ViewModel

    //Binding
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Подключение Binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Создание Scope для MainActivity
        val viewModel: MainViewModel by mainActivityScope.inject()
        model = viewModel

        if (savedInstanceState == null) {
            router.navigateTo(screens.mainScreen())
        }
    }

    override fun onResume() {
        //Установка навигатора
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        //Удаление навигатора
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

    override fun onDestroy() {
        //Удаление скоупа для данной активити
        mainActivityScope.close()
        super.onDestroy()
    }

}