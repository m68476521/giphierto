package com.morozco.domain.giftevents

import androidx.paging.PagingSource
import com.m68476521.networking.request.CategoryData
import com.morozco.core.model.Data

interface CategoriesRepository {
    suspend fun getCategories(): Result<CategoryData>

    fun pagingSourceForCategories(): PagingSource<Int, Data>
}