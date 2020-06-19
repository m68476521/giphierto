package com.m68476521.giphierto.api

object GiphyManager {
    const val URL = "https://api.giphy.com"

    lateinit var giphyApi: GiphyApi

    fun setToken(apiKey : String) {
        giphyApi = GiphyApi(GiphyService.create(URL,  apiKey))
    }
}