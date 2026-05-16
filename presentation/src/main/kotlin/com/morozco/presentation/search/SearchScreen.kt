package com.morozco.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.morozco.core.ui.GiphDialog
import com.morozco.core.ui.ShareUtils
import com.morozco.presentation.dashboard.LocalImagesViewModel
import com.morozco.presentation.dashboard.LocalPresentation

@Composable
fun SearchScreen(
    presentation: SearchPresentation = hiltViewModel<SearchViewModel>(),
    localPresentation: LocalPresentation = hiltViewModel<LocalImagesViewModel>()
) {
    val state by presentation.UIState.collectAsState()
    val localState by localPresentation.state.collectAsState()

    val lazyPagingItems = state.listOfImages.collectAsLazyPagingItems()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isFavorite = remember(localState.images, state.currentItemSelected?.id) {
        localState.images.any { it.id == state.currentItemSelected?.id }
    }

    println("MKE on Search Screen")

    if (state.currentItemSelected != null) {
        GiphDialog(
            image = state.currentItemSelected,
            isFavorite = isFavorite,
            onFavoriteClick = {
                state.currentItemSelected?.let { image ->
                    if (isFavorite) {
                        localPresentation.delete(image.id)
                        presentation.clearSelectedItem()
                    } else {
                        localPresentation.insert(image = image)
                    }
                }
            },
            onShareClick = {
                state.currentItemSelected?.images?.original?.url?.let { url ->
                    ShareUtils.shareImage(
                        context = context,
                        scope = scope,
                        url = url,
                        title = state.currentItemSelected?.title ?: ""
                    )
                }
            },
            onCloseClick = { presentation.clearSelectedItem() },
            onDismissRequest = { presentation.clearSelectedItem() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
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
        }
    }
}