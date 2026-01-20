package com.morozco.domain.giftevents

import com.morozco.core.model.ImageResponse

interface GiftRepositoryInterface {
    suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int,
    ): GiftEventsResult
}

sealed class GiftEventsResult {
    data class EventsFetched(
        val events: ImageResponse
    ) : GiftEventsResult()

    data object EmptyData : GiftEventsResult()

    data object Failure : GiftEventsResult()
}