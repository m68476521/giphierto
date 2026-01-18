package com.morozco.domain.giftevents

interface GiftRepositoryInterface {
    suspend fun getGiftEvents(): GiftEventsResult
}

sealed class GiftEventsResult {
    data class EventsFetched(
        val events: List<String>
    ) : GiftEventsResult()

    data object KitchenClosed : GiftEventsResult()

    data object Failure : GiftEventsResult()
}