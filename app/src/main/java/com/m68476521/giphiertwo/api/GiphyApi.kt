package com.m68476521.giphiertwo.api

import retrofit2.Response
import javax.inject.Inject

class GiphyApi
    @Inject
    constructor(
        private val giphyApi: GiphyService,
    ) : ApiHelper {
        override suspend fun getTrending(
            type: String,
            pagination: Int,
            limit: Int,
        ): Response<ImageResponse> = giphyApi.getTrending(type = type, pagination = pagination, limit = limit)

        override suspend fun getCategories(): Response<CategoryData> = giphyApi.categories()

        override suspend fun getSubCategories(subCategory: String): CategoryData = giphyApi.subCategories(subCategory)

        override suspend fun search(
            word: String,
            pagination: Int,
        ): ImageResponse = giphyApi.search(word, pagination = pagination)
    }
