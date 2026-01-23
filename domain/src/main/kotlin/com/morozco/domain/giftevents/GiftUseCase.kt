package com.morozco.domain.giftevents

import androidx.paging.PagingSource
import com.m68476521.networking.request.ImageResponse

class GiftUseCase(
    private val repository: GiftRepositoryInterface
) {
    suspend fun getGiftEvents(
        type: String,
        pagination: Int,
        limit: Int) {
        repository.getTrending(type, pagination, limit)
    }

    suspend fun pagingSourceForTrending(
        type: String,
        pagination: Int,
        limit: Int
    ) : PagingSource<Int, ImageResponse> {
        repository.pagingSourceForTrending(type, pagination, limit)
    }



}