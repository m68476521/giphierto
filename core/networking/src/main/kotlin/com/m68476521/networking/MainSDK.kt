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
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class MainSDK(
    val environment: Environment,
    private val client: HttpClient,
    private val json: Json,
) : MainAPI {
//    override suspend fun getOrderEvents(): Response<List<OrderEvent>> {
//        return executeRequest(GetOrderEvents)
//    }

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

            val jsonObject = json.decodeFromString<JsonObject>(
                json.encodeToString(request)
            )


//            try {
                // CHALLENGE-SHORTCUT: In a real application we would map all methods for REST in here
                when (request.method) {
                    RequestMethod.GET -> {
//                        val response = client.get(environment.baseURL + request.path)
                        //                    {
                        //                        parameter("api_key", BuildConfig.API_KEY)
                        //                    }

//                        try {
//                            val body = response.bodyAsText()
//                            val result = json.decodeFromString(request.serializer, body)
//                            return Response(
//                                responseCode = response.status.value,
//                                Result.success(result)
//                            )
//                        } catch (e: Exception) {
//                            // CHALLENGE-SHORTCUT: Serialization failed, in a real application we would log an error here and record some metrics
//                            // because this is a multiplatform module I can't do Logger.d, but I wouldn't do that any way, the right solution is for me
//                            // to define a logger interface and then each platform implements its own logging the right way. This is the reason why I call println
//                            println("Serialization failed")
//                            e.printStackTrace()
//                        }
                    }

                    RequestMethod.PUT -> {

                    }

                    else -> {

                    }
                }
//            } catch (e: Exception) {
//                // CHALLENGE-SHORTCUT: In a real application we would log an error here and record some metrics
//                println("Request failed")
//                e.printStackTrace()
//            }

            headers {
//                request.headers().
            }
        }

//        val result : KmpResult

        try {
            val response = when(request.method) {
                RequestMethod.GET -> client.get(request.path) {
//                    parameter("api_key", "PrY5WM3y9l8yYAMeRCuOHtbxJD8tiAOa")
                }
                RequestMethod.POST -> client.post(environment.baseURL + request.path, requestBuilder)
                RequestMethod.PUT -> client.put(environment.baseURL + request.path, requestBuilder)
                RequestMethod.DELETE -> client.delete(environment.baseURL + request.path, requestBuilder)
                RequestMethod.PATCH -> client.patch(environment.baseURL + request.path, requestBuilder)
                RequestMethod.HEAD -> client.head(environment.baseURL + request.path, requestBuilder)

            }
            println("MKE D")
            val body = response.bodyAsText()
            val result = json.decodeFromString(request.serializer, body)
            return Response(
                                responseCode = response.status.value,
                                Result.success(result)
                            )
        } catch (e: Exception) {
            println("MKE ::e  $e")
        }

        return Response(-1, result = Result.failure(Exception("Request failed")))
    }
}

