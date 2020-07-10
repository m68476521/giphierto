package com.m68476521.giphierto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.home.Home
import com.m68476521.giphierto.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

const val API_KEY = ""

class MainActivity : AppCompatActivity() {

    private val homeFragment = Home()
    private val searchFragment = SearchFragment()
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GiphyManager.setToken(API_KEY)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, homeFragment, getString(R.string.app_name)).hide(homeFragment)
            add(R.id.container, searchFragment, getString(R.string.home)).hide(searchFragment)

        }.commit()

        supportFragmentManager.beginTransaction().hide(activeFragment)
            .show(homeFragment).commit()
        activeFragment = homeFragment
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(homeFragment).commit()
                    activeFragment = homeFragment
                    return@OnNavigationItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(searchFragment).commit()
                    activeFragment = searchFragment
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
