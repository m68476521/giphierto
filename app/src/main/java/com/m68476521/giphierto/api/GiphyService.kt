package com.m68476521.giphierto.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GiphyService {

    companion object {
        fun create(url: String, apiKey: String = ""): GiphyService {
            val retrofit = Retrofit.Builder()
                .client(createClient(apiKey))
                .addCallAdapterFactory(RxErrorHandlerAdapter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createDateFormatter()))
                .baseUrl(url)
                .build()
            return retrofit.create(GiphyService::class.java)
        }

        private fun createClient(apiKey: String): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(RequestInterceptor(apiKey))
                .build()
        }

        private fun createDateFormatter(): Gson {
            return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create()
        }
    }

    @GET("/v1/gifs/trending")
    fun getTrending(): Single<ImageResponse>
}