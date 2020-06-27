package com.m68476521.giphierto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
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
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null)
            NavigationUI.setupWithNavController(bottom_navigation, navHostFragment.navController)
    }
}
