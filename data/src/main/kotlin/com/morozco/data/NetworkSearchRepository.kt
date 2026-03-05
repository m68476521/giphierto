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
        println("MKE:SEARCH here 002")
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
            offset = offset,
            pagination = pagination,
            limit = limit,
            api = api
        )
    }

}

class SearchPagingSource(
    private val search: String,
    private val offset: Int,
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
            val position = params.key ?: 0
            println("MKE:SEARCH here 000")

            println("MKE90005")
            val result = api.search2(
                search = search,
                rating = "",
                offset = position,
                limit = limit
            )
            println("MKE90005 MKE:T Loading page: $position")



            result.getOrNull()?.let { response ->
                println("MKE90005 MKE:T Loading data: ${response.data}")
                println("MKE90005 MKE:T Loading pagination: ${response.pagination}")
                val nextOffset = position + response.pagination.count
//                val nextKey = if (response.data.isEmpty()) null else nextPageNumber + 1
                return LoadResult.Page(
                    data = response.data,
//                    prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
//                    nextKey = nextKey
//                    prevKey = if (position == 0) null else position - params.loadSize,
                    prevKey = null,
                    nextKey = if (response.data.isEmpty()) null else (params.key ?: 0) + 1
                )
            }
            return LoadResult.Error(Exception("Something went wrong A"))//TODO check this
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}