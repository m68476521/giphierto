package com.morozco.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.toResult
import com.morozco.core.model.Image
import com.morozco.domain.giftevents.SearchRepository

class NetworkSearchRepository(
    private val api: MainAPIInterface,
): SearchRepository {
    override suspend fun search2(search: String,
                                 rating: String,
                                 offset: Int,
                                 limit: Int,): Result<ImageResponse> {
        return api.search2(
            search = search,
            rating = rating,
            offset = offset,
            limit = limit,
        ).toResult()
    }

    override fun pagingSourceForSearch(
        search: String,
        offset: Int,
        pagination: Int,
        limit: Int,
    ): PagingSource<Int, Image> {
        return SearchPagingSource(
            search = search,
            api = api
        )
    }

}

class SearchPagingSource(
    private val search: String,
    private val api: MainAPIInterface,
) : PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val position = params.key ?: 0
            val result = api.search2(
                search = search,
                rating = "",
                offset = position,
                limit = params.loadSize
            )

            result.getOrNull()?.let { response ->
                val nextOffset = position + response.pagination.count
                val totalCount = response.pagination.totalCount
                return LoadResult.Page(
                    data = response.data,
                    prevKey = if (position == 0) null else position - response.pagination.count,
                    nextKey = if (response.data.isEmpty() || (totalCount != null && nextOffset >= totalCount)) null else nextOffset
                )
            }
            return LoadResult.Error(Exception("Something went wrong loading search results"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}