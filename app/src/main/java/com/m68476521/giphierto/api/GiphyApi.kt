package com.m68476521.giphierto.api

class GiphyApi(private val giphyApi :GiphyService) {
    fun trending() = giphyApi.getTrending()
}