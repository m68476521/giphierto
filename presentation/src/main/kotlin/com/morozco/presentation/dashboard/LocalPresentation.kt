package com.morozco.presentation.dashboard

import com.morozco.core.model.Image
import kotlinx.coroutines.flow.StateFlow

interface LocalPresentation: LocalUIActions, LocalUIStateProvider

interface LocalUIActions {
    fun insert(image: Image)

    fun delete(id: String)

    suspend fun getImage(id: String): Image?
}

interface LocalUIStateProvider {
    val state: StateFlow<LocalUIState>
}

data class LocalUIState(
    val isLoading: Boolean = false,
    val images: List<Image> = emptyList()
)