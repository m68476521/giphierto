package com.morozco.presentation.favorites

import androidx.lifecycle.ViewModel
import com.morozco.core.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : ViewModel(), FavoritesPresentation {
    private val _uiState = MutableStateFlow(FavoritesUIState())

    override val state: StateFlow<FavoritesUIState> = _uiState

    override fun updateSelectedItem(image: Image) {
        _uiState.update {
            it.copy(
                currentImageSelected = image
            )
        }
    }

    override fun clearSelectedItem() {
        _uiState.update {
            it.copy(
                currentImageSelected = null
            )
        }
    }
}