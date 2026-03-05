package com.morozco.domain.giftevents

import androidx.paging.PagingSource
import com.m68476521.networking.request.ImageResponse
import com.morozco.core.model.Image

interface SearchRepository {
    suspend fun search2(
        search: String,
        rating: String,
        offset: Int,
        limit: Int,
    ): Result<ImageResponse>

    fun pagingSourceForSearch(
        search: String,
        offset: Int,
        pagination: Int,
        limit: Int,
    ): PagingSource<Int, Image>
}