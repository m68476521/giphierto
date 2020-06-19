package com.m68476521.giphierto.api

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor (
    private val apiKey: String = ""
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("Accept", "application/json")
            .header("Content-type", "application/json")
            .header("api_key", apiKey)
            .build()
        return chain.proceed(newRequest)
    }
}