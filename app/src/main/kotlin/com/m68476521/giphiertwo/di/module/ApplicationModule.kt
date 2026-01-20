package com.m68476521.giphiertwo.di.module

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.m68476521.giphiertwo.BuildConfig
import com.m68476521.giphiertwo.api.ApiHelper
import com.m68476521.giphiertwo.api.GiphyApi
import com.m68476521.giphiertwo.api.GiphyService
import com.m68476521.giphiertwo.api.RequestInterceptor
import com.m68476521.giphiertwo.data.AppDatabase
import com.m68476521.giphiertwo.data.ImageDao
import com.m68476521.networking.MainAPI
import com.m68476521.networking.MainSDK
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val interceptor = RequestInterceptor(BuildConfig.API_KEY)
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideDateFormatter(): Gson =
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String,
        dateFormatter: Gson,
    ): Retrofit =
        Retrofit
            .Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(dateFormatter))
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(GiphyService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: GiphyApi): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "images.db",
            ).build()

    @Provides
    fun provideImageDao(appDatabase: AppDatabase): ImageDao = appDatabase.imageDao()

//    @Provides
//    @Singleton
//    fun providesCSSAPI(
//        client: HttpClient,
//        json: Json,
//    ): MainAPI {
//        return MainSDK(
//            environment = Environment.fromString(BuildConfig.FLAVOR),
//            client = client,
//            json = json,
//        )
//    }
}
