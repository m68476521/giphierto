package com.morozco.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.toResult
import com.morozco.core.model.Data
import com.morozco.core.model.Image
import com.morozco.domain.giftevents.HomeRepository

class NetworkHomeRepository(
    private val api: MainAPIInterface,
): HomeRepository {
    override suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int
    ): Result<ImageResponse> {
        return api.getTrending(
            rating = "",
            type = type, offset = pagination, limit = limit).toResult()
    }

    override fun pagingSourceForTrending(
        type: String,
        pagination: Int,
        limit: Int
    ): PagingSource<Int, Image> {
        return HomePagingSource(
            type = type,
            pagination = pagination,
            limit = limit,
            api = api
        )
    }

    override suspend fun getCategories(): Result<CategoryData> {
        return api.getCategories().toResult()
    }

    override fun pagingSourceForCategories(): PagingSource<Int, Data> {
        return CategoriesPagingSource(limit = 25, api = api)
    }
}

class HomePagingSource(
    private val type: String,
    private val pagination: Int,
    private val limit: Int,
    private val api: MainAPIInterface,
) : PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val position = params.key ?: pagination
            val result = api.getTrending(
                rating = "",
                type = type, 
                offset = position, 
                limit = params.loadSize
            )

            result.getOrNull()?.let { response ->
                val nextOffset = position + response.pagination.count
                val totalCount = response.pagination.totalCount
                return LoadResult.Page(
                    data = response.data,
                    prevKey = if (position == pagination) null else position - response.pagination.count,
                    nextKey = if (response.data.isEmpty() || (totalCount != null && nextOffset >= totalCount)) null else nextOffset
                )
            }
            return LoadResult.Error(Exception("Something went wrong loading trending images"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

class CategoriesPagingSource(
    private val limit: Int,
    private val api: MainAPIInterface,
) : PagingSource<Int, Data>() {

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val result = api.getCategories()
            result.getOrNull()?.let { response ->
                return LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = null
                )
            }
            return LoadResult.Error(Exception("Something went wrong loading categories"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
