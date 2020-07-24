package com.m68476521.giphierto.api

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(
    private val apiKey: String = ""
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = chain.request().newBuilder().url(url)
            .header("Accept", "application/json")
            .header("Content-type", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}
