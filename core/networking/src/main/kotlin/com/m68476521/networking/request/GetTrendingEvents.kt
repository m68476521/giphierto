package com.m68476521.networking.request

import com.morozco.core.model.ImageResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

data class GetTrendingEvents(
    val type: String,
    val pagination: Int,
    val limit: Int,
) : Request<ImageResponse> {
    override val path: String = "/v1/gifs/trending"

    override val method = RequestMethod.GET

    override val serializer: KSerializer<ImageResponse>
        get() = ImageResponse.serializer()


//    @GET("/v1/gifs/trending")
//    suspend fun getTrending(
//        @Query("rating") type: String,
//        @Query("offset") pagination: Int,
//        @Query("limit") limit: Int,
//    ): Response<ImageResponse>
}
