package com.m68476521.giphierto.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.m68476521.giphierto.api.Image
import com.m68476521.giphierto.api.MainRepository
import com.m68476521.giphierto.util.Resource

class TrendingPaginationSource(
    private val repository: MainRepository
) : PagingSource<Int, Image>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Image> {
        return try {
            val nextPageNumber = params.key ?: 0
            val page = if (params.key == null) 0 else (params.key!! * 25)
            val response = repository.getTrending(pagination = page)
            val nextNumber = nextPageNumber.plus(1)
            when {
                response.isSuccessful -> {
                    val res = Resource.success(response.body()).data
                    if (res != null)
                        LoadResult.Page(
                            data = res.data,
                            prevKey = null, // Only paging forward.
                            nextKey = nextNumber
                        )
                    else returnEmpty(nextNumber)
                }
                else -> return returnEmpty(nextNumber)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    private fun returnEmpty(nextPageNumber: Int): LoadResult.Page<Int, Image> {
        return LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = nextPageNumber
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
