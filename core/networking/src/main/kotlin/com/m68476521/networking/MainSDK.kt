package com.m68476521.networking

import com.m68476521.networking.request.Environment
import com.m68476521.networking.request.GetTrendingEvents
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.NetworkResponse
import com.m68476521.networking.request.NetworkResult
import com.m68476521.networking.request.Request
import com.m68476521.networking.request.RequestMethod
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
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
        limit: Int,
    ): NetworkResult<ImageResponse> {
        val result = executeRequest(GetTrendingEvents(type, pagination, limit))
        return result as NetworkResult<ImageResponse>
    }

    private suspend fun <T> executeRequest(request: Request<T>): NetworkResult<NetworkResponse> {
        val executionResult: NetworkResult<NetworkResponse> =
            try {
                val response =
                    when (request.method) {
                        RequestMethod.GET -> client.get(request.path)
                        RequestMethod.POST -> client.post(environment.baseURL + request.path)
                        RequestMethod.PUT -> client.put(environment.baseURL + request.path)
                        RequestMethod.DELETE -> client.delete(environment.baseURL + request.path)
                        RequestMethod.PATCH -> client.patch(environment.baseURL + request.path)
                        RequestMethod.HEAD -> client.head(environment.baseURL + request.path)
                    }

                if (response.status.isSuccess()) {
                    val body = response.bodyAsText()
                    val decoded =
                        json.decodeFromString<NetworkResponse>(request.responseType(), body)
                    NetworkResult.Success(decoded)
                } else {
                    NetworkResult.Error(Exception("Request failed with status: ${response.status}"))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: ResponseException) {
                NetworkResult.Error(e)
            } catch (e: IOException) {
                NetworkResult.Error(e)
            } catch (e: SerializationException) {
                NetworkResult.Error(e)
            }

        return executionResult
    }
}
