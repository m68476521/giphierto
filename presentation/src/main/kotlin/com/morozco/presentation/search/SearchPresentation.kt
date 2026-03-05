package com.morozco.presentation.search

import androidx.paging.PagingData
import com.morozco.core.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SearchPresentation : SearchUIActions, SearchUIStateProvider

interface SearchUIActions {
    fun navigateBack()
}

interface SearchUIStateProvider {
    val UIState: StateFlow<SearchUIState>
}

data class SearchUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val listOfImages: Flow<PagingData<Image>>,
)