package com.morozco.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.morozco.presentation.dashboard.LocalImagesViewModel
import com.morozco.presentation.dashboard.LocalPresentation

@Composable
fun FavoritesScreen(
    localPresentation: LocalPresentation = hiltViewModel<LocalImagesViewModel>()
) {
    val state by localPresentation.state.collectAsState()

    println("MKE state ${state.images}")


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Favorites Screen")
    }
}