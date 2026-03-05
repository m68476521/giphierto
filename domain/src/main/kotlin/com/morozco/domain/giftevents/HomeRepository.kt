package com.morozco.domain.giftevents

import androidx.paging.PagingSource
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.ImageResponse
//import com.morozco.core.model.CategoryData
import com.morozco.core.model.Data
import com.morozco.core.model.Image


interface HomeRepository {
    suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int,
    ): Result<ImageResponse>

    fun pagingSourceForTrending(
        type: String,
        pagination: Int,
        limit: Int,
    ): PagingSource<Int, Image>

    suspend fun getCategories(): Result<CategoryData>

    fun pagingSourceForCategories(): PagingSource<Int, Data>
}

//sealed class GiftEventsResult {
//    data class EventsFetched(
//        val events: ImageResponse
//    ) : GiftEventsResult()
//
//    data object EmptyData : GiftEventsResult()
//
//    data object Failure : GiftEventsResult()
//}