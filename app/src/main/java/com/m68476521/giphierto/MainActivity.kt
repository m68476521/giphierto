package com.m68476521.giphierto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.m68476521.giphierto.api.GiphyManager
import kotlinx.android.synthetic.main.activity_main.*

const val API_KEY = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GiphyManager.setToken(API_KEY)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment?
        if (navHostFragment != null)
            NavigationUI.setupWithNavController(bottom_navigation, navHostFragment.navController)

        setActionBarNav()
    }

    private fun setActionBarNav() {
        val navController = findNavController(R.id.navHostFragment)
        bottom_navigation.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
    }
}
