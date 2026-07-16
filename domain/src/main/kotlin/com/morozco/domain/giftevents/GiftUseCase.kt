package com.morozco.domain.giftevents

import androidx.paging.PagingSource
import com.m68476521.networking.request.ImageResponse
import com.morozco.core.model.Image

class GiftUseCase(
    private val repository: GiftRepositoryInterface,
) {
    suspend fun getGiftEvents(
        type: String,
        pagination: Int,
        limit: Int,
    ): GetGiftEventsResult {
        val result = repository.getTrending(type, pagination, limit)

        result.getOrNull()?.let {
            return GetGiftEventsResult.FetchingSuccess(it)
        } ?: run {
            return GetGiftEventsResult.EmptyData
        }
    }

    fun pagingSourceForTrending(
        type: String,
        pagination: Int,
        limit: Int,
    ): PagingSource<Int, Image> = repository.pagingSourceForTrending(type, pagination, limit)

    sealed class GetGiftEventsResult {
        data class FetchingSuccess(
            val events: ImageResponse,
        ) : GetGiftEventsResult()

        data object EmptyData : GetGiftEventsResult()

        data object Failure : GetGiftEventsResult()
    }
}
