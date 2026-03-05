package com.morozco.presentation.search.domain

import androidx.paging.PagingSource
import com.m68476521.networking.request.ImageResponse
import com.morozco.core.model.Image
import com.morozco.domain.giftevents.SearchRepository

class SearchUseCase (
    private val repository: SearchRepository
) {
    suspend fun search2(): SearchResult {
        println("MKE:SEARCH here 003")
        val result = repository.search2(
            search = "Goku",
            rating = "",
            offset = 0,
            limit = 25,
        )

        result.getOrNull()?.let {
            return SearchResult.SearchSuccess(it)
        }
        return SearchResult.SearchFailure
    }

     fun pagingSourceForSearch(
         search: String,
         offset: Int,
         pagination: Int,
         limit: Int,
     ): PagingSource<Int, Image> {
        return repository.pagingSourceForSearch(search, offset, pagination, limit)
    }
}


sealed class SearchResult {
    data class SearchSuccess(
        val events: ImageResponse
    ) : SearchResult()

    data object EmptyData : SearchResult()

    data object SearchFailure : SearchResult()

}