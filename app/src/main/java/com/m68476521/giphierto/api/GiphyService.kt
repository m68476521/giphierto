package com.m68476521.giphierto.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphyService {
    @GET("/v1/gifs/trending")
    suspend fun getTrending(
        @Query("rating") type: String,
        @Query("offset") pagination: Int,
        @Query("limit") limit: Int
    ): Response<ImageResponse>

    @GET("/v1/gifs/search")
    suspend fun search(
        @Query("q") q: String,
        @Query("limit") limit: Int = 30,
        @Query("rating") type: String = Rating.G.name,
        @Query("offset") pagination: Int
    ): ImageResponse

    @GET("/v1/gifs/categories")
    suspend fun categories(): Response<CategoryData>

    @GET("/v1/gifs/categories/{category}")
    suspend fun subCategories(@Path("category") category: String): CategoryData
}
