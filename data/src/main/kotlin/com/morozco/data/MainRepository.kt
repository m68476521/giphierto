package com.m68476521.data

import androidx.paging.PagingSource
import com.m68476521.networking.MainAPI
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.NetworkResult
import com.morozco.core.model.Image
import com.morozco.data.TrendingPaginationSource
import com.morozco.domain.giftevents.GiftRepositoryInterface

class MainRepository(
    private val api: MainAPI,
) : GiftRepositoryInterface {
    override suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int
    ): NetworkResult<ImageResponse> {
        TODO("Not yet implemented")
    }
//    override suspend fun getTrending(
//        type: String,
//        pagination: Int,
//        limit: Int
//    ): NetworkResult<ImageResponse> {
//        val result = api.getTrending(type = type, pagination = pagination, limit = limit)
////        if (result.isSuccess) {
////            result.getOrNull()?.let { data ->
////                return if (data.data.isEmpty()) {
////                    GiftEventsResult.EmptyData
////                } else {
////                    GiftEventsResult.EventsFetched(data)
////                }
////            }
////        }
//
////        return GiftEventsResult.Failure
//        return result as NetworkResult<ImageResponse>
//    }

    override suspend fun pagingSourceForTrending(
        type: String,
        pagination: Int,
        limit: Int
    ): PagingSource<Int, Image> {
        return TrendingPaginationSource(
            type = type,
            pagination = pagination,
            limit = limit,
            repository = api,
        )
    }
}