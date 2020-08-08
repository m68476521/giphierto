package com.m68476521.giphierto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.home.TrendingFragmentDirections
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
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment?
            val navCo = navHostFragment!!.navController
            when (item.itemId) {
                R.id.homeFragment -> {
                    navCo.popBackStack()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.searchFragment -> {
                    val next = TrendingFragmentDirections.actionTrendingFragmentToSearchFragment2()
                    navCo.navigate(next)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
