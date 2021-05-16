package com.m68476521.giphierto.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.m68476521.giphierto.databinding.FragmentFavoritesBinding
import com.m68476521.giphierto.di.GiphApplication
import com.m68476521.giphierto.models.FavoriteViewModelFactory
import com.m68476521.giphierto.models.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private val favoritesModel: FavoritesViewModel by viewModels {
        FavoriteViewModelFactory((requireActivity().application as GiphApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = FavoriteAdapter()
        binding.favoritesImages.setHasFixedSize(true)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.favoritesImages.layoutManager = staggeredGridLayoutManager
        binding.favoritesImages.adapter = adapter

        subscribeUi(adapter)
        postponeEnterTransition()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startPostponedEnterTransitions()
    }

    private fun startPostponedEnterTransitions() {
        (requireView().parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun subscribeUi(adapter: FavoriteAdapter) {
        favoritesModel.favorites.observe(
            viewLifecycleOwner,
            Observer { images ->
                images?.let {
                    adapter.submitList(it)
                }
            }
        )
    }
}
