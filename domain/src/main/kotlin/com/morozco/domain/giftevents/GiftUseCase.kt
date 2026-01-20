package com.morozco.domain.giftevents

class GiftUseCase(
    private val repository: GiftRepositoryInterface
) {
    suspend fun getGiftEvents(
        type: String,
        pagination: Int,
        limit: Int) {
        repository.getTrending(type, pagination, limit)
    }

}