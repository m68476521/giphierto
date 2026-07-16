package com.morozco.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.morozco.core.model.Image

private const val DIALOG_WIDTH_FRACTION = 0.9f
private const val DIALOG_HEIGHT_FRACTION = 0.5f

data class GiphDialogActions(
    val onFavoriteClick: () -> Unit,
    val onShareClick: () -> Unit,
    val onCloseClick: () -> Unit,
)

@Composable
@Suppress("FunctionNaming")
fun GiphDialog(
    image: Image?,
    isFavorite: Boolean,
    actions: GiphDialogActions,
    onDismissRequest: () -> Unit,
) {
    if (image == null) return

    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier.fillMaxWidth(DIALOG_WIDTH_FRACTION)) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(DIALOG_HEIGHT_FRACTION)
                        .background(MaterialTheme.colorScheme.surface),
            ) {
                GiphDialogHeader(
                    isFavorite = isFavorite,
                    onFavoriteClick = actions.onFavoriteClick,
                    onShareClick = actions.onShareClick,
                    onCloseClick = actions.onCloseClick,
                )
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = image.images?.original?.url,
                    contentDescription = image.title,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
@Suppress("FunctionNaming")
private fun GiphDialogHeader(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Favorite Button",
            )
        }

        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share Button",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        IconButton(onClick = onCloseClick) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close Button",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
