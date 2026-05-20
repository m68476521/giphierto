package com.morozco.presentation.dashboard


import androidx.paging.PagingData
import com.morozco.core.model.Data
import com.morozco.core.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DashboardPresentation: DashboardUIActions, DashboardUIStateProvider

interface DashboardUIActions {
    fun navigateToNext()

    fun updateSelectedItem(item: Image)

    fun clearSelectedItem()

    fun isFavorite(isFavorite: Boolean)

    fun goToDetails(image: Image)
}

interface DashboardUIStateProvider {
    val state: StateFlow<DashboardUIState>
}

data class DashboardUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentItemSelected: Image? = null,
    val isFavorite: Boolean = false,
    val listOfImages: Flow<PagingData<Image>>,
    val listOfCategories: Flow<PagingData<Data>>,
)