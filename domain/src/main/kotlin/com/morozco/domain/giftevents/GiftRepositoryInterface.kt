package com.morozco.domain.giftevents

import androidx.paging.PagingSource
import com.m68476521.networking.request.ImageResponse
import com.morozco.core.model.Image

interface GiftRepositoryInterface {
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
}
