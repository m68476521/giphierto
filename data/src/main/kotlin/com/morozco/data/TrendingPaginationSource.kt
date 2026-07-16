package com.morozco.data

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.m68476521.networking.MainAPI
import com.morozco.core.model.Image
import java.io.IOException

// The latest one

@Deprecated("This is not being used")
class TrendingPaginationSource(
    private val type: String,
    private val pagination: Int,
    private val limit: Int,
    private val mainAPI: MainAPI,
) : PagingSource<Int, Image>() {
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> =
        try {
            val nextPageNumber = (params.key ?: 0) + 1
            val result = mainAPI.getTrending(type = type, pagination = nextPageNumber, limit = limit)
            val response = result.getOrNull()

            if (response != null) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = nextPageNumber,
                )
            } else {
                LoadResult.Error(Exception("Something went wrong loading trending images"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
}
