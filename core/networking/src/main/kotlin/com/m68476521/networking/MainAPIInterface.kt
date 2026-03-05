package com.m68476521.networking

import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.NetworkResult


interface MainAPIInterface {
    suspend fun getTrending(
        rating: String,
        type: String,
        offset: Int,
        limit: Int,
    ): NetworkResult<ImageResponse>

    suspend fun getCategories(): NetworkResult<CategoryData>

    suspend fun search2(
        search: String,
        rating: String,
        offset: Int,
        limit: Int,
    ): NetworkResult<ImageResponse>
}