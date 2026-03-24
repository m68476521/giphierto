package com.morozco.domain.navigation

import com.morozco.core.model.Subcategories
import kotlinx.coroutines.flow.Flow

interface Navigator {
    val navigationEvents: Flow<NavigationEvent>

    suspend fun navigateBack()

    fun navigateToDashboard()

    fun navigateToSearch()

    fun navigateToCategories()

    fun navigateToSubCategories(subcategory: String)
}

sealed class NavigationEvent {
    data object NavigateBack: NavigationEvent()

    data class NavigateTo(val screen: Any): NavigationEvent()
}