package com.morozco.domain

import kotlinx.coroutines.flow.Flow

interface Navigator {
    val navigationEvents: Flow<NavigationEvent>

    suspend fun navigateBack()

    suspend fun navigateToDashboard()
}

sealed class NavigationEvent {
    data object NavigateBack: NavigationEvent()

    data class NavigateTo(val screen: Any): NavigationEvent()
}