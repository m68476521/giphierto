package com.morozco.presentation.categories.domain

import androidx.paging.PagingSource
import com.morozco.core.model.SubCategoryData
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

    fun pagingSourceForSubcategories(
        category: String,
        pagination: Int,
        limit: Int
    ): PagingSource<Int, SubCategoryData> {
        return repository.pagingSourceSubCategories(category, pagination, limit)
    }

    suspend fun getSubcategories(category: String): GetSubcategoriesResult {
        val result = repository.getSubCategories(category)
        result.getOrNull()?.let {
            return GetSubcategoriesResult.FetchingSuccess(it.data)
        }
        return GetSubcategoriesResult.Failure

    }
}

sealed class GetSubcategoriesResult {
    data class FetchingSuccess(
        val events: List<SubCategoryData>
    ) : GetSubcategoriesResult()

    data object EmptyData : GetSubcategoriesResult()

    data object Failure : GetSubcategoriesResult()

}