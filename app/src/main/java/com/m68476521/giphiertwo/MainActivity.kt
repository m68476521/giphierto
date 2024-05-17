package com.m68476521.giphiertwo

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.m68476521.giphiertwo.databinding.ActivityMainBinding
import com.m68476521.giphiertwo.util.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Stack

@AndroidEntryPoint
class MainActivity :
    AppCompatActivity(),
    // TODO: Fix this deprecated
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private val backStack = Stack<Int>()

    private val indexToPage = mapOf(0 to R.id.homePage, 1 to R.id.categoriesPage, 2 to R.id.favoritesPage)

    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_home_base, R.id.nav_host_home),
        BaseFragment.newInstance(R.layout.content_search_base, R.id.nav_host_search),
        BaseFragment.newInstance(R.layout.content_favorites_base, R.id.nav_host_search)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val itemId = indexToPage[position] ?: R.id.homePage
                if (binding.bottomNavigation.selectedItemId != itemId)
                    binding.bottomNavigation.selectedItemId = itemId
            }
        })
        binding.mainPager.adapter = ViewPagerAdapter()
        binding.mainPager.offscreenPageLimit = fragments.size

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.bottomNavigation.setOnNavigationItemReselectedListener(this)

        if (backStack.empty()) backStack.push(0)
    }

    override fun onBackPressed() {//TODO check this deprecated
        val fragment = fragments[binding.mainPager.currentItem]
        val navigatedUp = fragment.onBackPressed()
        if (!navigatedUp) {
            if (backStack.size > 1) {
                backStack.pop()
                binding.mainPager.currentItem = backStack.peek()
            } else super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (binding.mainPager.currentItem != position) setItem(position)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position]
        fragment.popToRoot()
    }

    private fun setItem(position: Int) {
        binding.mainPager.currentItem = position
        backStack.push(position)
    }

    inner class ViewPagerAdapter : FragmentStateAdapter(supportFragmentManager, lifecycle) {

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }
}
