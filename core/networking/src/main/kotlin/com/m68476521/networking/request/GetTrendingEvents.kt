package com.m68476521.networking.request

import com.morozco.core.model.Image
import com.morozco.core.model.Pagination
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetTrendingEvents(
    val type: String,
    val pagination: Int,
    val limit: Int,
) : Request<ImageResponse> {
    override val path: String = "/v1/gifs/trending"

    override val method = RequestMethod.GET

    override fun <T : NetworkResponse> responseType(): KSerializer<T> {
        @Suppress("UNCHECKED_CAST")
        return ImageResponse.serializer() as KSerializer<T>
    }

    //    override val serializer: KSerializer<ImageResponse>
//        get() = ImageResponse.serializer()
//    override fun <T : Response> responseType(): KSerializer<T> {
//        @Suppress("UNCHECKED_CAST")
//        return ImageResponse.serializer() as KSerializer<T>
//
//    }

}


@Serializable
data class ImageResponse(
    val data: List<Image>,
    val pagination: Pagination,
): NetworkResponse
