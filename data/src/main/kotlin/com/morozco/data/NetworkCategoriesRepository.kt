package com.morozco.data

import androidx.paging.PagingSource
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.toResult
import com.morozco.core.model.Data
import com.morozco.domain.giftevents.CategoriesRepository

class NetworkCategoriesRepository(
    private val api: MainAPIInterface,
): CategoriesRepository {
    override suspend fun getCategories(): Result<CategoryData> {
        return api.getCategories().toResult()
    }

    override fun pagingSourceForCategories(): PagingSource<Int, Data> {
        return CategoriesPagingSource(limit = 25, api = api)
    }
}