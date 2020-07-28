package com.m68476521.giphierto.api

class GiphyApi(private val giphyApi: GiphyService) {
    fun trending(type: String = Rating.PG_13.rating, pagination: Int = 0, limit: Int = 25) =
        giphyApi.getTrending(type = type, pagination = pagination, limit = limit)

    fun search(word: String, pagination: Int) = giphyApi.search(word, pagination = pagination)

    fun categories() = giphyApi.categories()
}
