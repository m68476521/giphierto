package com.m68476521.giphierto

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.util.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Stack

const val API_KEY = ""

class MainActivity :
    AppCompatActivity(),
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val backStack = Stack<Int>()

    private val indexToPage = mapOf(0 to R.id.homePage, 1 to R.id.categoriesPage, 2 to R.id.favoritesPage)

    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_home_base, R.id.nav_host_home),
        BaseFragment.newInstance(R.layout.content_search_base, R.id.nav_host_search),
        BaseFragment.newInstance(R.layout.content_favorites_base, R.id.nav_host_search)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GiphyManager.setToken(API_KEY)
        main_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val itemId = indexToPage[position] ?: R.id.homePage
                if (bottom_navigation.selectedItemId != itemId)
                    bottom_navigation.selectedItemId = itemId
            }
        })
        main_pager.adapter = ViewPagerAdapter()
        main_pager.offscreenPageLimit = fragments.size

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        bottom_navigation.setOnNavigationItemReselectedListener(this)

        if (backStack.empty()) backStack.push(0)
    }

    override fun onBackPressed() {
        val fragment = fragments[main_pager.currentItem]
        val navigatedUp = fragment.onBackPressed()
        if (!navigatedUp) {
            if (backStack.size > 1) {
                backStack.pop()
                main_pager.currentItem = backStack.peek()
            } else super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (main_pager.currentItem != position) setItem(position)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position]
        fragment.popToRoot()
    }

    private fun setItem(position: Int) {
        main_pager.currentItem = position
        backStack.push(position)
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(supportFragmentManager, lifecycle) {

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }
}
