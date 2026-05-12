package com.morozco.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.morozco.core.ui.GiphDialog
import com.morozco.presentation.dashboard.LocalImagesViewModel
import com.morozco.presentation.dashboard.LocalPresentation

@Composable
fun FavoritesScreen(
    favoritesPresentation: FavoritesPresentation = hiltViewModel<FavoritesViewModel>(),
    localPresentation: LocalPresentation = hiltViewModel<LocalImagesViewModel>()
) {
    val state by localPresentation.state.collectAsState()
    val favoritesState by favoritesPresentation.state.collectAsState()

    val isFavorite = remember(state.images, favoritesState.currentImageSelected?.id) {
        state.images.any { it.id == favoritesState.currentImageSelected?.id }
    }

    if (favoritesState.currentImageSelected != null) {
        GiphDialog(
            image = favoritesState.currentImageSelected,
            isFavorite = isFavorite,
            onFavoriteClick = {
                favoritesState.currentImageSelected?.let { image ->
                    if (isFavorite) {
                        localPresentation.delete(image.id)
                        favoritesPresentation.clearSelectedItem()
                    } else {
                        localPresentation.insert(image = image)
                    }
                }
            },
            onShareClick = { /* Handle share */ },
            onCloseClick = { favoritesPresentation.clearSelectedItem() },
            onDismissRequest = { favoritesPresentation.clearSelectedItem() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (state.images.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Favorites empty")
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
            ) {
                items(
                    count = state.images.size,
                ) { idx ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(12.dp),
                        shape = RectangleShape,
                        onClick = {
                            val itemClicked = state.images[idx]
                            itemClicked.let { image ->
                                favoritesPresentation.updateSelectedItem(image)
                            }
                        },
                    ) {
                        AsyncImage(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                            model = state.images[idx].images?.fixedHeightDownsampled?.url,
                            contentDescription = state.images[idx].title,
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}