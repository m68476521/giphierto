package com.m68476521.giphiertwo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.m68476521.giphiertwo.R
import com.m68476521.giphiertwo.data.Image

@Composable
fun ImageDialog(
    isFavorite: Boolean,
    uuId: String,
    fixedHeightDownsampledUrl: String,
    originalUrl: String,
    fixedHeightUrl: String,
    title: String,
    onDelete: (String) -> Unit,
    onClose: () -> Unit,
    onAddToFavorite: (Image) -> Unit,
) {
    Dialog(onDismissRequest = {
        onClose()
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
                            if (!isFavorite) {
                                val image =
                                    Image(
                                        uid = uuId,
                                        fixedHeightDownsampled = fixedHeightDownsampledUrl,
                                        originalUrl = originalUrl,
                                        title = title,
                                    )
                                onAddToFavorite(image)
                            } else {
                                onDelete(uuId)
                            }
                        },
                    ) {
                        Icon(
                            imageVector =
                                if (isFavorite) {
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
                            onClose()
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
                    model = fixedHeightUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}
