package com.morozco.presentation.dashboard


import com.morozco.core.model.Image
import kotlinx.coroutines.flow.StateFlow

interface DashboardPresentation: DashboardUIActions, DashboardUIStateProvider

interface DashboardUIActions {
    fun navigateToNext()
}

interface DashboardUIStateProvider {
    val state: StateFlow<DashboardUIState>
}

data class DashboardUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentItemSelected: Image? = null,
)