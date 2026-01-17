package com.m68476521.giphiertwo.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.m68476521.giphiertwo.databinding.FragmentFavoritesBinding
import com.m68476521.giphiertwo.models.FavoriteIntent
import com.m68476521.giphiertwo.models.FavoritesViewModel
import com.m68476521.giphiertwo.models.LocalImagesViewModel
import com.m68476521.giphiertwo.ui.components.FavoriteTileCard
import com.m68476521.giphiertwo.ui.components.ImageDialog
import com.m68476521.giphiertwo.ui.theme.giphiertwoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val favoritesModel: FavoritesViewModel by viewModels()

    // TODO merge this 2 view models into 1
    private val favoritesViewModel: LocalImagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =
            FragmentFavoritesBinding.inflate(inflater, container, false).apply {
                composeView.apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
                    )
                    setContent {
                        giphiertwoTheme {
                            SideEffect {
                                favoritesModel.getData()
                            }

                            val listOfFavorites = favoritesModel.myFavorites.collectAsState()

                            val state by favoritesModel.state.collectAsState()

                            val stateLocal by favoritesViewModel.state.collectAsState()

                            if (state.currentItemSelected != null) {
                                favoritesViewModel.imageById(state.currentItemSelected?.uid ?: "")
                                ImageDialog(
                                    isFavorite = stateLocal.isFavorite,
                                    uuId = state.currentItemSelected?.uid ?: "",
                                    fixedHeightDownsampledUrl = state.currentItemSelected?.fixedHeightDownsampled ?: "",
                                    originalUrl = state.currentItemSelected?.originalUrl ?: "",
                                    fixedHeightUrl = state.currentItemSelected?.fixedHeightDownsampled ?: "",
                                    title = state.currentItemSelected?.title ?: "",
                                    onDelete = { uuId ->
                                        favoritesViewModel.deleteById(uuId)
                                        favoritesModel.getData()
                                    },
                                    onClose = {
                                        favoritesModel.handleIntent(
                                            FavoriteIntent.ClearItemSelected,
                                        )
                                    },
                                    onAddToFavorite = { image ->
                                        favoritesViewModel.insert(image)
                                    },
                                )
                            }

                            LazyVerticalStaggeredGrid(
                                columns = StaggeredGridCells.Fixed(3),
                            ) {
                                items(listOfFavorites.value.size) { idx ->
                                    FavoriteTileCard(item = listOfFavorites.value[idx]) {
                                        favoritesModel.handleIntent(
                                            FavoriteIntent.SelectItem(listOfFavorites.value[idx]),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        return binding.root
    }
}
