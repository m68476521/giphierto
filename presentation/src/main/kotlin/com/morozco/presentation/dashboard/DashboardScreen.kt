package com.morozco.presentation.dashboard

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import util.getPreferredUrl

@Composable
fun DashboardScreen(
    presentation: DashboardPresentation = hiltViewModel<DashboardViewModel>()
) {

    val state by presentation.state.collectAsState()

    val lazyPagingItems = state.listOfImages.collectAsLazyPagingItems()

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
//                                if (!favoriteState.isFavorite) {
//                                    val image =
//                                        Image(
//                                            uid =
//                                                state.currentItemSelected?.id
//                                                    ?: "",
//                                            fixedHeightDownsampled =
//                                                state.currentItemSelected
//                                                    ?.images
//                                                    ?.fixedHeightDownsampled
//                                                    ?.url
//                                                    ?: "",
//                                            originalUrl =
//                                                state.currentItemSelected
//                                                    ?.images
//                                                    ?.original
//                                                    ?.url
//                                                    ?: "",
//                                            title =
//                                                state.currentItemSelected?.title
//                                                    ?: "",
//                                        )
//                                    favoritesViewModel.insert(
//                                        image = image,
//                                    )
//                                } else {
//                                    favoritesViewModel.deleteById(
//                                        state.currentItemSelected?.id ?: "",
//                                    )
//                                }
                            },
                        ) {
                            Icon(
                                imageVector =
//                                    if (favoriteState.isFavorite) {
//                                        Icons.Filled.Favorite
//                                    } else {
                                    Icons.Filled.FavoriteBorder,
//                                    },
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = "Favorite Button",
                            )
                        }

                        IconButton(
                            onClick = {
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = "Share Button",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }

                        IconButton(
                            onClick = {
                                presentation.clearSelectedItem()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close Button",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = state.currentItemSelected?.getPreferredUrl(),
                        contentDescription = state.currentItemSelected?.title,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }

    if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Blue)
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
            ) {
                items(
                    count = lazyPagingItems.itemCount,
                ) { idx ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(12.dp),
                        shape = RectangleShape,
                        onClick = {
                            val itemClicked = lazyPagingItems[idx]
                            itemClicked?.let { item ->
                                presentation.updateSelectedItem(item)
                            }
                        },
                    ) {
                        AsyncImage(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                            model = lazyPagingItems[idx]?.images?.fixedHeightDownsampled?.url,
                            contentDescription = lazyPagingItems[idx]?.title,
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }

}