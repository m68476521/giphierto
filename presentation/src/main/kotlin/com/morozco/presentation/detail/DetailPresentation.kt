package com.morozco.presentation.detail

import com.morozco.core.model.Image
import kotlinx.coroutines.flow.StateFlow

interface DetailPresentation: DetailUIActions, DetailUIStateProvider

interface DetailUIActions {
    suspend fun goBack()
}

interface DetailUIStateProvider{
    val state: StateFlow<DetailUIState>
}

data class DetailUIState(
    val isLoading: Boolean = false,
    val image: Image? = null,
)