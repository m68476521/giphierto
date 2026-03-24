package com.morozco.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.SubCategoryDataResponse
import com.m68476521.networking.request.toResult
import com.morozco.core.model.Data
import com.morozco.core.model.SubCategoryData
import com.morozco.domain.giftevents.CategoriesRepository

class NetworkCategoriesRepository(
    private val api: MainAPIInterface,
) : CategoriesRepository {
    override suspend fun getCategories(): Result<CategoryData> {
        return api.getCategories().toResult()
    }

    override fun pagingSourceForCategories(): PagingSource<Int, Data> {
        return CategoriesPagingSource(limit = 25, api = api)
    }


    override suspend fun getSubCategories(subCategory: String): Result<SubCategoryDataResponse> {
        return api.getSubCategories(category = subCategory, offset = 0, limit = 25).toResult()

    }

    override fun pagingSourceSubCategories(
        category: String, pagination: Int,
        limit: Int
    ): PagingSource<Int, SubCategoryData> {
        return SubCategoriesPagingSource(
            category = category,
            pagination = pagination,
            limit = limit,
            api = api
        )
    }
}


class SubCategoriesPagingSource(
    private val category: String,
    private val pagination: Int,
    private val limit: Int,
    private val api: MainAPIInterface,
) : PagingSource<Int, SubCategoryData>() {

    override fun getRefreshKey(state: PagingState<Int, SubCategoryData>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SubCategoryData> {
        return try {
            val position = params.key ?: pagination
            val result = api.getSubCategories(
                category = category,
                offset = position,
                limit = params.loadSize
            )

            result.getOrNull()?.let { response ->
                val nextOffset = position + response.pagination.count
                val totalCount = response.pagination.totalCount
                return LoadResult.Page(
                    data = response.data,
                    prevKey = if (position == pagination) null else position - response.pagination.count,
                    nextKey = if (response.data.isEmpty()) null else nextOffset
                )
            }
            return LoadResult.Error(Exception("Something went wrong loading trending images"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
