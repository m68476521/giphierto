package com.m68476521.giphiertwo.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.m68476521.giphiertwo.data.Image

@Suppress("FunctionName")
@Composable
fun FavoriteTileCard(
    item: Image,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        shape = RectangleShape,
        onClick = {
            onClick()
        },
    ) {
        AsyncImage(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            model = item.fixedHeightDownsampled,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
        )
    }
}
