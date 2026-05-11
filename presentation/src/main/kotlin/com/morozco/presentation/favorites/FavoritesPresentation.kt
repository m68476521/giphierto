package com.morozco.presentation.favorites

import com.morozco.core.model.Image
import kotlinx.coroutines.flow.StateFlow

interface FavoritesPresentation : FavoritesUIActions, FavoritesUIStateProvider

interface FavoritesUIActions {
    fun updateSelectedItem(image: Image)

    fun clearSelectedItem()
}

interface FavoritesUIStateProvider {
    val state: StateFlow<FavoritesUIState>
}

data class FavoritesUIState(
    val isLoading: Boolean = false,
    val currentImageSelected: Image? = null
)