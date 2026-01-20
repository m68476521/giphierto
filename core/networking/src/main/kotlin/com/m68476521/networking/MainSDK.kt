package com.m68476521.networking

import com.m68476521.networking.request.Environment
import com.m68476521.networking.request.GetTrendingEvents
import com.m68476521.networking.request.Request
import com.m68476521.networking.request.RequestMethod
import com.m68476521.networking.request.Response
import com.morozco.core.model.ImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class MainSDK(
    val environment: Environment,
    private val client: HttpClient,
    private val json: Json,
) : MainAPI {

    override suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int
    ): Response<ImageResponse> {
        return executeRequest(GetTrendingEvents(type, pagination, limit))
    }

    private suspend fun <T> executeRequest(request: Request<T>): Response<T> {

        val requestBuilder: HttpRequestBuilder.() -> Unit = {
            contentType(ContentType.Application.Json)

            when (request.method) {
                RequestMethod.GET -> {

                }

                RequestMethod.PUT -> {

                }

                else -> {

                }
            }
        }


        try {
            val response = when (request.method) {
                RequestMethod.GET -> client.get(request.path)
                RequestMethod.POST -> client.post(
                    environment.baseURL + request.path,
                    requestBuilder
                )

                RequestMethod.PUT -> client.put(environment.baseURL + request.path, requestBuilder)
                RequestMethod.DELETE -> client.delete(
                    environment.baseURL + request.path,
                    requestBuilder
                )

                RequestMethod.PATCH -> client.patch(
                    environment.baseURL + request.path,
                    requestBuilder
                )

                RequestMethod.HEAD -> client.head(
                    environment.baseURL + request.path,
                    requestBuilder
                )

            }
            val body = response.bodyAsText()
            val result = json.decodeFromString(request.serializer, body)
            return Response(
                responseCode = response.status.value,
                Result.success(result)
            )
        } catch (e: Exception) {
        }

        return Response(-1, result = Result.failure(Exception("Request failed")))
    }
}

