package com.m68476521.giphierto.api

class GiphyApi(private val giphyApi: GiphyService) {
    suspend fun trending(type: String = Rating.PG_13.rating, pagination: Int = 0, limit: Int = 25) =
        giphyApi.getTrending(type = type, pagination = pagination, limit = limit)

    suspend fun search(word: String, pagination: Int) = giphyApi.search(word, pagination = pagination)

    fun categories() = giphyApi.categories()

    fun subCategories(category: String) = giphyApi.subCategories(category)
}
