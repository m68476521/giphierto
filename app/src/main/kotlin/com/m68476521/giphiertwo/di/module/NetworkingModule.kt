package com.m68476521.giphiertwo.di.module

import android.util.Log
import com.m68476521.giphiertwo.BuildConfig
import com.m68476521.networking.MainAPI
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.MainSDK
import com.m68476521.networking.MainSDK2
import com.m68476521.networking.request.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Provides
    @Singleton
    fun providesHTTPClient(): HttpClient =
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true // Giphy sends a lot of extra metadata
                        prettyPrint = true
                        isLenient = true
                    },
                )
            }

            install(Logging) {
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Log.d("HTTP Client", message)
                        }
                    }
                level = LogLevel.ALL
            }

            defaultRequest {
                url {
                    val base = Environment.fromString(BuildConfig.FLAVOR).baseURL

                    takeFrom(base)

                    parameters.append("api_key", BuildConfig.API_KEY)
                }
                header(HttpHeaders.Accept, ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }

    @Provides
    @Singleton
    fun providesJSON(): Json =
        Json {
            ignoreUnknownKeys = true
        }

    @Provides
    @Singleton
    fun providesMainAPI(
        client: HttpClient,
        json: Json,
    ): MainAPI =
        MainSDK(
            environment = Environment.fromString(BuildConfig.FLAVOR),
            client = client,
            json = json,
        )

    @Provides
    @Singleton
    fun providesMainAPIInterface(
        client: HttpClient,
        json: Json,
    ): MainAPIInterface =
        MainSDK2(
            environment = Environment.fromString(BuildConfig.FLAVOR),
            client = client,
            json = json,
        )
}
