package com.morozco.presentation.subcategories

import androidx.paging.PagingData
import com.morozco.core.model.Data
import com.morozco.core.model.SubCategoryData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SubCategoriesPresentation : SubCategoriesUIActions, SubCategoriesUIStateProvider

interface SubCategoriesUIActions {
    fun navigateToNext(word: String)
}

interface SubCategoriesUIStateProvider {
    val state: StateFlow<SubCategoriesUIState>
}

data class SubCategoriesUIState(
    val isLoading: Boolean = false,
//    val subCategories: List<Subcategories> = emptyList(),
    val listOfSubCategories: Flow<PagingData<SubCategoryData>>,
    val listOfCategories: List<Data> = emptyList(),
)