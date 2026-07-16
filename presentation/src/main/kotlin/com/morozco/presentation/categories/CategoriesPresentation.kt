package com.morozco.presentation.categories

import com.morozco.core.model.Data
import kotlinx.coroutines.flow.StateFlow

interface CategoriesPresentation :
    CategoriesUIActions,
    CategoriesUIStateProvider

interface CategoriesUIActions {
    fun navigateToSubCategories(subcategory: String)
}

interface CategoriesUIStateProvider {
    val state: StateFlow<CategoriesUIState>
}

data class CategoriesUIState(
    val isLoading: Boolean = false,
    val listOfCategories: List<Data> = emptyList(),
)
