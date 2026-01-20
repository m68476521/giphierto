package com.m68476521.data

import com.m68476521.networking.MainAPI
import com.morozco.core.model.Rating
import com.morozco.domain.giftevents.GiftEventsResult
import com.morozco.domain.giftevents.GiftRepositoryInterface

class MainRepository(
    private val api: MainAPI,
): GiftRepositoryInterface {
    override suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int
    ): GiftEventsResult {
        val result = api.getTrending(type = type, pagination = pagination, limit = limit).result
        if (result.isSuccess) {
            result.getOrNull()?.let { data ->
                return if (data.data.isEmpty()) {
                    GiftEventsResult.EmptyData
                } else {
                    GiftEventsResult.EventsFetched(data)
                }
            }
        }

        return GiftEventsResult.Failure
    }

//    suspend fun getCategories() = apiHelper.getCategories()

//    suspend fun getSubCategories(subcategory: String) = apiHelper.getSubCategories(subcategory)
//
//    suspend fun search(
//        word: String,
//        pagination: Int,
//    ) = apiHelper.search(word, pagination)
}


//    private val api: CSSAPI,
//) : OrderEventsRepositoryInterface {
//    override suspend fun getOrderEvents(): OrderEventsResult {
//        val result = api.getOrderEvents().result
//
//        if (result.isSuccess) {
//            result.getOrNull()?.let { events ->
//                return if (events.isEmpty()) {
//                    OrderEventsResult.KitchenClosed
//                } else {
//                    OrderEventsResult.EventsFetched(events)
//                }
//            }
//        }
//
//        return OrderEventsResult.Failure
//    }
//}