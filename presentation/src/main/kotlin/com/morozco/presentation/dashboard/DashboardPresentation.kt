package com.morozco.presentation.dashboard

import kotlinx.coroutines.flow.StateFlow

interface DashboardPresentation: DashboardUIActions, DashboardUIStateProvider

interface DashboardUIActions {
    fun navigateToNext()
}

interface DashboardUIStateProvider {
    val state: StateFlow<DashboardUIState>
}

data class DashboardUIState(
    val isLoading: Boolean = false
)