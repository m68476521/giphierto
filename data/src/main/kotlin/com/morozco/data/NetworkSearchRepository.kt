package com.morozco.data

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.toResult
import com.morozco.core.model.Image
import com.morozco.domain.giftevents.SearchRepository
import java.io.IOException

class NetworkSearchRepository(
    private val api: MainAPIInterface,
) : SearchRepository {
    override suspend fun search2(
        search: String,
        rating: String,
        offset: Int,
        limit: Int,
    ): Result<ImageResponse> =
        api
            .search2(
                search = search,
                rating = rating,
                offset = offset,
                limit = limit,
            ).toResult()

    override fun pagingSourceForSearch(
        search: String,
        offset: Int,
        pagination: Int,
        limit: Int,
    ): PagingSource<Int, Image> =
        SearchPagingSource(
            search = search,
            api = api,
        )
}

class SearchPagingSource(
    private val search: String,
    private val api: MainAPIInterface,
) : PagingSource<Int, Image>() {
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? = state.anchorPosition

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> =
        try {
            val position = params.key ?: 0
            val result =
                api.search2(
                    search = search,
                    rating = "",
                    offset = position,
                    limit = params.loadSize,
                )

            val response = result.getOrNull()

            if (response != null) {
                val nextOffset = position + response.pagination.count
                val totalCount = response.pagination.totalCount
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (position == 0) null else position - response.pagination.count,
                    nextKey =
                        if (response.data.isEmpty() ||
                            (totalCount != null && nextOffset >= totalCount)
                        ) {
                            null
                        } else {
                            nextOffset
                        },
                )
            } else {
                LoadResult.Error(Exception("Something went wrong loading search results"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
}
