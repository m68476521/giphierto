package com.morozco.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.morozco.core.model.Data
import com.morozco.core.ui.GiphDialog

@Composable
fun DashboardScreen(
    presentation: DashboardPresentation = hiltViewModel<DashboardViewModel>(),
    localPresentation: LocalPresentation = hiltViewModel<LocalImagesViewModel>()
) {

    val state by presentation.state.collectAsState()

    val localState by localPresentation.state.collectAsState()

    val lazyPagingItems = state.listOfImages.collectAsLazyPagingItems()

    val lazyCategoriesPagingItems = state.listOfCategories.collectAsLazyPagingItems()

    var selectedCategory by remember { mutableStateOf<Data?>(null) }
    val currentId = state.currentItemSelected?.id

    val isFavorite = remember(localState.images, currentId) {
        localState.images.any { it.id == currentId }
    }

    if (state.currentItemSelected != null) {
        GiphDialog(
            image = state.currentItemSelected,
            isFavorite = isFavorite,
            onFavoriteClick = {
                state.currentItemSelected?.let { image ->
                    if (isFavorite) {
                        localPresentation.delete(image.id)
                    } else {
                        localPresentation.insert(image = image)
                    }
                }
            },
            onShareClick = { /* Handle share */ },
            onCloseClick = { presentation.clearSelectedItem() },
            onDismissRequest = { presentation.clearSelectedItem() }
        )
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
//                .background(Blue)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(lazyCategoriesPagingItems.itemCount) { idx ->
                        val category = lazyCategoriesPagingItems.get(idx)
                        println("MKE category ::::: $category")
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = {
                                Text(text = category?.name.orEmpty(),
//                                    modifier = Modifier.background()
                                )
                            }
                        )
                    }
                }

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(3),
                ) {
                    items(
                        count = lazyPagingItems.itemCount,
//                        key = { index -> lazyPagingItems[index]?.id ?: index }
//                        key = { index ->
//                            val item = lazyPagingItems.peek(index) // Peek doesn't trigger a load
//                            item?.id ?: index
//                        }
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

    println("MKE categories:::: ${lazyPagingItems.itemCount}")



}