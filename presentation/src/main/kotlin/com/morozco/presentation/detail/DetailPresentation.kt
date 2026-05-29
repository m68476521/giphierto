package com.morozco.presentation.detail

import com.morozco.core.model.Image
import kotlinx.coroutines.flow.StateFlow

interface DetailPresentation: DetailUIActions, DetailUIStateProvider

interface DetailUIActions {
    suspend fun goBack()
}

interface DetailUIStateProvider{
    val uiState: StateFlow<DetailUIState>
}

data class DetailUIState(
    val isLoading: Boolean = false,
    val image: Image? = null,
    val relatedGiftList: List<Image> = emptyList()
)