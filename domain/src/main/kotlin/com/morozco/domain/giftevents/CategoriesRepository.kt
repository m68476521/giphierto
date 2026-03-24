package com.morozco.domain.giftevents

import androidx.paging.PagingSource
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.SubCategoryDataResponse
import com.morozco.core.model.Data
import com.morozco.core.model.SubCategoryData

interface CategoriesRepository {
    suspend fun getCategories(): Result<CategoryData>

    suspend fun getSubCategories(subCategory: String): Result<SubCategoryDataResponse>

    fun pagingSourceForCategories(): PagingSource<Int, Data>

    fun pagingSourceSubCategories(category: String,
                                  pagination: Int,
                                  limit: Int,): PagingSource<Int, SubCategoryData>
}