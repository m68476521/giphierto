package com.m68476521.giphierto

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.util.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Stack

const val API_KEY = ""

class MainActivity :
    AppCompatActivity(),
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val backStack = Stack<Int>()

    private val indexToPage = mapOf(0 to R.id.homePage, 1 to R.id.searchPage, 2 to R.id.favoritesPage)

    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_home_base, R.id.nav_host_home),
        BaseFragment.newInstance(R.layout.content_search_base, R.id.nav_host_search),
        BaseFragment.newInstance(R.layout.content_favorites_base, R.id.nav_host_search)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GiphyManager.setToken(API_KEY)
        main_pager.addOnPageChangeListener(this)
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

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(page: Int) {
        val itemId = indexToPage[page] ?: R.id.homePage
        if (bottom_navigation.selectedItemId != itemId)
            bottom_navigation.selectedItemId = itemId
    }

    private fun setItem(position: Int) {
        main_pager.currentItem = position
        backStack.push(position)
    }

    inner class ViewPagerAdapter : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size
    }
}
