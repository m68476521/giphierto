package com.m68476521.networking

import com.m68476521.networking.request.Response
import com.morozco.core.model.ImageResponse

interface MainAPI {
    suspend fun getTrending(
//        type: String = Rating.PG_13.rating,
//        pagination: Int = 0,
//        limit: Int = 25,
        type: String,
        pagination: Int,
        limit: Int,
    ): Response<ImageResponse>

}