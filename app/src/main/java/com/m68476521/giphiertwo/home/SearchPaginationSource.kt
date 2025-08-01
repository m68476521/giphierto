package com.m68476521.giphiertwo.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.m68476521.giphiertwo.api.Image
import com.m68476521.giphiertwo.api.MainRepository

class SearchPaginationSource(
    private val word: String,
    private val repository: MainRepository,
) : PagingSource<Int, Image>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> =
        try {
            val nextPageNumber = params.key ?: 0
            val page = if (params.key == null) 0 else (params.key!! * 25)
            val response = repository.search(word, page)
            LoadResult.Page(
                data = response.data,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}
