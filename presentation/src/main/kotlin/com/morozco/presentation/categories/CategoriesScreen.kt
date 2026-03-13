package com.morozco.presentation.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage

@Composable
fun CategoriesScreen(
    presentation: CategoriesPresentation = hiltViewModel<CategoriesViewModel>()
) {
    val state by presentation.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.listOfCategories.isEmpty()) {
            // TODO: Add a spinner
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
            ) {
                items(
                    count = state.listOfCategories.size,
                ) { idx ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(12.dp),
                        shape = RectangleShape,
                        onClick = {
                            val itemClicked = state.listOfCategories[idx]
                            itemClicked?.let {
                                // TODO: Implement Navigation to subcategories
                            }
                        },
                    ) {
                        val currentCategory = state.listOfCategories[idx]

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center

                        ) {

                            AsyncImage(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                model = currentCategory.gif.images.fixedHeightDownSampled?.url,
                                contentDescription = currentCategory.name,
                                contentScale = ContentScale.Crop,
                            )

                            Text(
                                text = currentCategory.name
                            )
                        }
                    }
                }
            }
        }
    }
}