package com.m68476521.networking

import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.NetworkResult

@Deprecated("Stop using this, use MainRepository")
interface MainAPI {
    suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int,
    ): NetworkResult<ImageResponse>
}
