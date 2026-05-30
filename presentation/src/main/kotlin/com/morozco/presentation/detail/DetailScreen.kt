package com.morozco.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.morozco.core.model.Image
import com.morozco.core.ui.ShareUtils
import com.morozco.presentation.dashboard.LocalImagesViewModel
import com.morozco.presentation.dashboard.LocalPresentation

@Composable
fun DetailScreen(
    presentation: DetailPresentation = hiltViewModel<DetailViewModel>(),
    localPresentation: LocalPresentation = hiltViewModel<LocalImagesViewModel>()
) {
    val state by presentation.uiState.collectAsState()
    val localState by localPresentation.state.collectAsState()

    val currentId = state.image?.id

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val isFavorite = remember(localState.images, currentId) {
        localState.images.any { it.id == currentId }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(6.dp),

            ) {
            state.image?.let { image ->
                AsyncImage(
                    model = image.images?.original?.url,
                    contentDescription = image.title,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    text = image.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

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
                            state.image?.let { image ->
                                if (isFavorite) {
                                    localPresentation.delete(image.id)
                                } else {
                                    localPresentation.insert(image = image)
                                }
                            }
                        },
                    ) {
                        Icon(
                            imageVector = if (isFavorite) {
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
                            state.image?.images?.original?.url?.let { url ->
                                ShareUtils.shareImage(
                                    context = context,
                                    scope = scope,
                                    url = url,
                                    title = state.image?.title ?: ""
                                )
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share Button",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    TextButton(
                        onClick = {
                            // Handle copy action here
                        }
                    ) {
                        Text(
                            text = "Copy",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Text(
                    text = "Related Gifts",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(state.relatedGiftList.size) { idx ->
                        RelatedCardGift(state.relatedGiftList[idx])
                    }
                }
            }
        }
    }
}

@Composable
fun RelatedCardGift(image: Image) {
    Box(modifier = Modifier.size(100.dp)) {
        AsyncImage(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            model = image.images?.fixedHeightDownsampled?.url,
            contentDescription = image.title,
            contentScale = ContentScale.Crop,
        )
    }
}