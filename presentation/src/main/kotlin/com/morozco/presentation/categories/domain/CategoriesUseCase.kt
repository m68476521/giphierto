package com.morozco.presentation.categories.domain

import com.morozco.domain.giftevents.CategoriesRepository
import com.morozco.presentation.dashboard.domain.HomeUseCase.GetCategoriesResult

class CategoriesUseCase(
    private val repository: CategoriesRepository
) {
    suspend fun getCategories(): GetCategoriesResult {
        val result = repository.getCategories()
        result.getOrNull()?.let {
            return GetCategoriesResult.FetchingSuccess(it)
        }
        return GetCategoriesResult.Failure
    }
}