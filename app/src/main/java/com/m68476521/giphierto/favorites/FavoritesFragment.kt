package com.m68476521.giphierto.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        favoritesImages.setHasFixedSize(true)
        favoritesImages.layoutManager = staggeredGridLayoutManager

        favoritesImages.adapter = imagesAdapter

        postponeEnterTransition()

        if (!this::favoritesModel.isInitialized) initialLoad()
        else startPostponedEnterTransitions()
    }

    private fun startPostponedEnterTransitions() {
        (requireView().parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun initialLoad() {
        favoritesModel = ViewModelProvider(this).get(LocalImagesViewModel::class.java)
        GlobalScope.launch {
            imagesAdapter.addAll(
                favoritesModel.getFavorites()
            )
            startPostponedEnterTransitions()
        }
    }
}
