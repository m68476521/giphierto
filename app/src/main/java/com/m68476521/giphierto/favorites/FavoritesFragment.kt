package com.m68476521.giphierto.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.m68476521.giphierto.R
import com.m68476521.giphierto.models.LocalImagesViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {
    private var imagesAdapter = FavoriteAdapter()
    private lateinit var favoritesModel: LocalImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onResume() {
        super.onResume()

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        favoritesImages.setHasFixedSize(true)
        favoritesImages.layoutManager = staggeredGridLayoutManager

        favoritesImages.adapter = imagesAdapter

        favoritesModel = ViewModelProvider(this).get(LocalImagesViewModel::class.java)
        initialLoad()
    }

    private fun initialLoad() {
        GlobalScope.launch {
            imagesAdapter.addAll(
                favoritesModel.getFavorites()
            )
        }
    }
}