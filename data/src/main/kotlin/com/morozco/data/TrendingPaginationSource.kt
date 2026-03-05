package com.morozco.data

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.m68476521.networking.MainAPI
import com.morozco.core.model.Image

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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            println("MKE900010")
            val nextPageNumber = (params.key ?: 0) + 1
            val result = mainAPI.getTrending(type = type, pagination = nextPageNumber, limit = limit)
//            val nextNumber = nextPageNumber.plus(1)
//            when {
//                response.isSuccessful -> {
//                    val res = Resource.success(response.body()).data
//                    if (res != null) {
//                        LoadResult.Page(
//                            data = res.data,
//                            prevKey = null, // Only paging forward.
//                            nextKey = nextNumber,
//                        )
//                    } else {
//                        returnEmpty(nextNumber)
//                    }
//                }
//                else -> return returnEmpty(nextNumber)
//            }
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