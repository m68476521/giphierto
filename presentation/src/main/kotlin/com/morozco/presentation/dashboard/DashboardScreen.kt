package com.morozco.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage

@Composable
fun DashboardScreen(
    presentation: DashboardPresentation = hiltViewModel<DashboardViewModel>()
) {
    Text(modifier = Modifier.background(Color.Red), text = "Dashboard")

    val state by presentation.state.collectAsState()



//    private val trendingModel: TrendingViewModel by viewModels()

//    val lazyPagingItems = trendingModel.flow.collectAsLazyPagingItems()


//    val state by trendingModel.state.collectAsState()

//    val favoriteState by favoritesViewModel.state.collectAsState()

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
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_share_white_18dp),
//                                contentDescription = "Share Button",
//                                tint = MaterialTheme.colorScheme.onSurface,
//                            )
                        }

                        IconButton(
                            onClick = {
//                                trendingModel.handleIntent(
//                                    com.m68476521.giphiertwo.models.TrendingIntent.ClearItemSelected,
//                                )
                                presentation.navigateToNext()
                            },
                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_close_white_24dp),
//                                contentDescription = "Close Button",
//                                tint = MaterialTheme.colorScheme.onSurface,
//                            )
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

//    if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center,
//        ) {
//            CircularProgressIndicator()
//        }
//    } else {
//        LazyVerticalStaggeredGrid(
//            columns = StaggeredGridCells.Fixed(3),
//        ) {
//            items(count = lazyPagingItems.itemCount) { idx ->
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    elevation = CardDefaults.cardElevation(12.dp),
//                    shape = RectangleShape,
//                    onClick = {
//                        val itemClicked = lazyPagingItems[idx]
//                        itemClicked?.let { image ->
//                            trendingModel.handleIntent(
//                                com.m68476521.giphiertwo.models.TrendingIntent.SelectItem(image),
//                            )
//                        }
//                    },
//                ) {
//                    AsyncImage(
//                        modifier =
//                            Modifier
//                                .fillMaxWidth()
//                                .wrapContentHeight(),
//                        model = lazyPagingItems[idx]?.images?.fixedHeightDownsampled?.url,
//                        contentDescription = lazyPagingItems[idx]?.title,
//                        contentScale = ContentScale.Crop,
//                    )
//                }
//            }
//        }
//    }

}