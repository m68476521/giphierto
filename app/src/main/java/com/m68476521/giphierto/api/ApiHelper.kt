package com.m68476521.giphierto.api

interface ApiHelper {
    suspend fun getTrending(type: String, pagination: Int, limit: Int): ImageResponse

    suspend fun getCategories(): CategoryData

    suspend fun getSubCategories(subCategory: String): CategoryData

    suspend fun search(word: String, pagination: Int): ImageResponse
}
