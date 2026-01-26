package com.morozco.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.toResult
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
        return api.getTrending(type = type, pagination = pagination, limit = limit).toResult()
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
}

class HomePagingSource(
    private val type: String,
    private val pagination: Int,
    private val limit: Int,
    private val api: MainAPIInterface,
) : PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val nextPageNumber = (params.key ?: 0) + 1
            val result = api.getTrending(type = type, pagination = pagination, limit = limit)
            result.getOrNull()?.let { response ->
                return LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = nextPageNumber
                )
            }
            return LoadResult.Error(Exception("Something went wrong A"))//TODO check this
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun returnEmpty(nextPageNumber: Int): LoadResult.Page<Int, Image> =
        LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = nextPageNumber,
        )
}