package com.m68476521.networking.request

import com.morozco.core.model.ImageResponse
import kotlinx.serialization.KSerializer

data class GetTrendingEvents(
    val type: String,
    val pagination: Int,
    val limit: Int,
) : Request<ImageResponse> {
    override val path: String = "/v1/gifs/trending"

    override val method = RequestMethod.GET

    override val serializer: KSerializer<ImageResponse>
        get() = ImageResponse.serializer()

}
