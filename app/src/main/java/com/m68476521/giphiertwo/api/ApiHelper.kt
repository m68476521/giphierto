package com.m68476521.giphiertwo.api

import retrofit2.Response

interface ApiHelper {
    suspend fun getTrending(type: String, pagination: Int, limit: Int): Response<ImageResponse>

    suspend fun getCategories(): Response<CategoryData>

    suspend fun getSubCategories(subCategory: String): CategoryData

    suspend fun search(word: String, pagination: Int): ImageResponse
}
