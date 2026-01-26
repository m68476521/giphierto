package com.m68476521.giphiertwo.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.m68476521.giphiertwo.R
import com.m68476521.giphiertwo.data.Image
import com.m68476521.giphiertwo.databinding.FragmentTrendingBinding
import com.m68476521.giphiertwo.models.LocalImagesViewModel
import com.m68476521.giphiertwo.models.TrendingIntent
import com.m68476521.giphiertwo.models.TrendingViewModel
import com.m68476521.giphiertwo.ui.theme.GiphiertwoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private val trendingModel: TrendingViewModel by viewModels()

    private val favoritesViewModel: LocalImagesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // TODO on rotation crash

        binding =
            FragmentTrendingBinding.inflate(inflater, container, false).apply {
                composeView.apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
                    )

                    setContent {
                        GiphiertwoTheme {
//                            val lazyPagingItems = trendingModel.flow.collectAsLazyPagingItems()

                            val state by trendingModel.state.collectAsState()

                            val favoriteState by favoritesViewModel.state.collectAsState()

                            if (state.currentItemSelected != null) {
                                Dialog(onDismissRequest = {
                                }) {
                                    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
                                        Column(
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(0.5f)
                                                    .background(MaterialTheme.colorScheme.surface),
                                        ) {
                                            Row(
                                                modifier =
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                IconButton(
                                                    onClick = {
                                                        if (!favoriteState.isFavorite) {
                                                            val image =
                                                                Image(
                                                                    uid =
                                                                        state.currentItemSelected?.id
                                                                            ?: "",
                                                                    fixedHeightDownsampled =
                                                                        state.currentItemSelected
                                                                            ?.images
                                                                            ?.fixedHeightDownsampled
                                                                            ?.url
                                                                            ?: "",
                                                                    originalUrl =
                                                                        state.currentItemSelected
                                                                            ?.images
                                                                            ?.original
                                                                            ?.url
                                                                            ?: "",
                                                                    title =
                                                                        state.currentItemSelected?.title
                                                                            ?: "",
                                                                )
                                                            favoritesViewModel.insert(
                                                                image = image,
                                                            )
                                                        } else {
                                                            favoritesViewModel.deleteById(
                                                                state.currentItemSelected?.id ?: "",
                                                            )
                                                        }
                                                    },
                                                ) {
                                                    Icon(
                                                        imageVector =
                                                            if (favoriteState.isFavorite) {
                                                                Icons.Filled.Favorite
                                                            } else {
                                                                Icons.Filled.FavoriteBorder
                                                            },
                                                        tint = MaterialTheme.colorScheme.onSurface,
                                                        contentDescription = "Favorite Button",
                                                    )
                                                }

                                                IconButton(
                                                    onClick = {
                                                    },
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_share_white_18dp),
                                                        contentDescription = "Share Button",
                                                        tint = MaterialTheme.colorScheme.onSurface,
                                                    )
                                                }

                                                IconButton(
                                                    onClick = {
                                                        trendingModel.handleIntent(
                                                            TrendingIntent.ClearItemSelected,
                                                        )
                                                    },
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_close_white_24dp),
                                                        contentDescription = "Close Button",
                                                        tint = MaterialTheme.colorScheme.onSurface,
                                                    )
                                                }
                                            }

                                            AsyncImage(
                                                modifier = Modifier.fillMaxWidth(),
                                                model =
                                                    state.currentItemSelected
                                                        ?.images
                                                        ?.fixedHeight
                                                        ?.url,
                                                contentDescription = state.currentItemSelected?.title,
                                                contentScale = ContentScale.Crop,
                                            )
                                        }
                                    }
                                }
                            }
                            /*
                            if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                LazyVerticalStaggeredGrid(
                                    columns = StaggeredGridCells.Fixed(3),
                                ) {
//                                    items(count = lazyPagingItems.itemCount) { idx ->
//                                        Card(
//                                            modifier = Modifier.fillMaxWidth(),
//                                            elevation = CardDefaults.cardElevation(12.dp),
//                                            shape = RectangleShape,
//                                            onClick = {
//                                                val itemClicked = lazyPagingItems[idx]
//                                                itemClicked?.let { image ->
//                                                    trendingModel.handleIntent(
//                                                        TrendingIntent.SelectItem(image),
//                                                    )
//                                                }
//                                            },
//                                        ) {
//                                            AsyncImage(
//                                                modifier =
//                                                    Modifier
//                                                        .fillMaxWidth()
//                                                        .wrapContentHeight(),
//                                                model = lazyPagingItems[idx]?.images?.fixedHeightDownsampled?.url,
//                                                contentDescription = lazyPagingItems[idx]?.title,
//                                                contentScale = ContentScale.Crop,
//                                            )
//                                        }
//                                    }
                                }
                            } */
                        }
                    }
                }
            }
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuGoToSearch) {
            val next = TrendingFragmentDirections.actionTrendingToSearchFragment()
            requireView().findNavController().navigate(next)
            return false
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater,
    ) {
        inflater.inflate(R.menu.menu_trending, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.menuGoToSearch).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnClickListener {
            val next = TrendingFragmentDirections.actionTrendingToSearchFragment()
            it.findNavController().navigate(next)
        }
    }
}
