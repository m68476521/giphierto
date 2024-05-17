package com.m68476521.giphiertwo.api

import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getTrending(
        type: String = Rating.PG_13.rating,
        pagination: Int = 0,
        limit: Int = 25
    ) = apiHelper.getTrending(type = type, pagination = pagination, limit = limit)

    suspend fun getCategories() = apiHelper.getCategories()

    suspend fun getSubCategories(subcategory: String) = apiHelper.getSubCategories(subcategory)

    suspend fun search(word: String, pagination: Int) = apiHelper.search(word, pagination)
}
