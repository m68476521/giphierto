package com.morozco.presentation.dashboard

import com.morozco.core.model.Image
import kotlinx.coroutines.flow.StateFlow

interface LocalPresentation: LocalUIActions, LocalUIStateProvider

interface LocalUIActions {
    fun insert(image: Image)

    fun delete(id: String)

    fun getImage(id: String)
}

interface LocalUIStateProvider {
    val state: StateFlow<LocalUIState>
}

data class LocalUIState(
    val isLoading: Boolean = false,
    val images: List<Image> = emptyList()
)