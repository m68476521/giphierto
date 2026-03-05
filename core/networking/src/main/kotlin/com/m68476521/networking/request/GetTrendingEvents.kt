package com.m68476521.networking.request

import com.morozco.core.model.Image
import com.morozco.core.model.Pagination
import com.morozco.core.model.Rating
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetTrendingEvents(
    val rating: String = Rating.PG_13.rating,
    val offset: Int,
    val limit: Int,
//    val count: Int = 20,
//    val total_count: Int = 25
) : Request<ImageResponse> {
    override val path: String = "/v1/gifs/trending"

    override val method = RequestMethod.GET

    override fun <T : NetworkResponse> responseType(): KSerializer<T> {
        @Suppress("UNCHECKED_CAST")
        return ImageResponse.serializer() as KSerializer<T>
    }


    override val headers: List<Pair<String, String>> = listOf(
        "offset" to offset.toString(),
        "limit" to limit.toString(),
        "rating" to rating,
    )

    override val parameters: Map<String, String>
        get() = mapOf(
            "offset" to offset.toString(),
            "limit" to limit.toString(),
            "rating" to rating,
        )

}


@Serializable
data class ImageResponse(
    val data: List<Image>,
    val pagination: Pagination,
) : NetworkResponse
