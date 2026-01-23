package com.m68476521.networking

import com.m68476521.networking.request.Environment
import com.m68476521.networking.request.GetTrendingEvents
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.NetworkResponse
import com.m68476521.networking.request.NetworkResult
import com.m68476521.networking.request.Request
import com.m68476521.networking.request.RequestMethod
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
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

@Suppress("UNCHECKED_CAST")
class MainSDK(
    val environment: Environment,
    private val client: HttpClient,
    private val json: Json,
) : MainAPI {

    override suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int
    ): NetworkResult<ImageResponse> {
//        return executeRequest(GetTrendingEvents(type, pagination, limit))
        val result = executeRequest(GetTrendingEvents(type, pagination, limit))

        return result as NetworkResult<ImageResponse>
    }

    private suspend fun <T> executeRequest(request: Request<T>): NetworkResult<NetworkResponse> {

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

            if (response.status.isSuccess()) {
                val body = response.bodyAsText()
                val result = json.decodeFromString<NetworkResponse>(request.responseType(), body)
                return NetworkResult.Success(result)
            } else {
                return NetworkResult.Error(Exception("Request failed"))
            }

//            NetworkResponse(
//                responseCode = response.status.value,
//                Result.success(result)

        } catch (e: Exception) {
                    return NetworkResult.Error(e)
        }

//        return Response(-1, result = Result.failure(Exception("Request failed")))
//        return NetworkResult.Error()
    }
}

