package com.m68476521.networking

// import com.morozco.core.model.CategoryData
import android.util.Log
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.Environment
import com.m68476521.networking.request.GetCategories2
import com.m68476521.networking.request.GetRelated
import com.m68476521.networking.request.GetSearchImages
import com.m68476521.networking.request.GetSubCategories
import com.m68476521.networking.request.GetTrendingEvents
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.NetworkResponse
import com.m68476521.networking.request.NetworkResult
import com.m68476521.networking.request.NetworkResult.Error
import com.m68476521.networking.request.NetworkResult.Success
import com.m68476521.networking.request.RelatedData
import com.m68476521.networking.request.Request
import com.m68476521.networking.request.RequestMethod
import com.m68476521.networking.request.SubCategoryDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.isSuccess
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.util.UUID
import kotlin.math.min

@Suppress("UNCHECKED_CAST")
class MainSDK2(
    val environment: Environment,
    private val client: HttpClient,
    private val json: Json,
) : MainAPIInterface {
    override suspend fun getTrending(
        rating: String,
        type: String,
        offset: Int,
        limit: Int,
    ): NetworkResult<ImageResponse> {
        println("MKE90002")
        println("MKE90002 MKE200 MKE here on GetTrending $type, $offset, $limit")
        val result = executeRequest(GetTrendingEvents(type, offset, limit))

        return result as NetworkResult<ImageResponse>
    }

    override suspend fun getCategories(): NetworkResult<CategoryData> {
        val result = executeRequest(GetCategories2)
        if (result is Success) {
            println("MKE resultQ: $result")
        } else if (result is Error) {
            println("MKE resultP: ${result.error}")
        }
        println("MKE result: $result")
        return result as NetworkResult<CategoryData>
    }

    override suspend fun search2(
        search: String,
        rating: String,
        offset: Int,
        limit: Int,
    ): NetworkResult<ImageResponse> {
        println("MKE:SEARCH here 001")
        val result =
            executeRequest(
                GetSearchImages(
                    q = search,
                    rating = rating,
                    offset = offset,
                    limit = limit,
                    randomId = UUID.randomUUID().toString(),
                ),
            )
        return result as NetworkResult<ImageResponse>
    }

    override suspend fun getSubCategories(
        category: String,
        offset: Int,
        limit: Int,
    ): NetworkResult<SubCategoryDataResponse> {
        val result = executeRequest(GetSubCategories(category, offset, limit))
        return result as NetworkResult<SubCategoryDataResponse>
    }

    override suspend fun getRelated(
        giftId: String,
        limit: Int,
    ): NetworkResult<RelatedData> {
        val result = executeRequest(GetRelated(giftId, limit))
        return result as NetworkResult<RelatedData>
    }

    private suspend fun <T> executeRequest(request: Request<T>): NetworkResult<NetworkResponse> {
        println("MKE this one 0>")
        val requestBuilder: HttpRequestBuilder.() -> Unit = {
//            contentType(ContentType.Application.Json)

//            val jsonObject = json.decodeFromString<JsonObject>(
//                json.encodeToString(request)
//            )

//            when (request.method) {
//                RequestMethod.GET -> {
//                    // No operation
//                    setBody(jsonObject)
//                }
//
//
//                RequestMethod.POST,
//                RequestMethod.PUT,
//                RequestMethod.PATCH,
//                RequestMethod.DELETE -> {
//                    setBody(jsonObject)
//
//                }
//
//                RequestMethod.HEAD -> {
//                    // No operation
//                }
//            }

            headers {
                request.headers.forEach { (key, value) ->
                    header(key, value)
                }
            }

            url {
                request.parameters.forEach { (key, value) ->
                    parameters.append(key, value)
                }
            }

            if (request.method != RequestMethod.GET) {
                contentType(ContentType.Application.Json)
                // Encode the request data class to a JSON string or map
                val jsonObject = json.encodeToString(request)
                setBody(jsonObject)
            }
        }

        try {
            val response =
                when (request.method) {
                    RequestMethod.GET -> client.get(request.path, block = requestBuilder)
                    RequestMethod.POST ->
                        client.post(
                            environment.baseURL + request.path,
                        )

                    RequestMethod.PUT -> client.put(environment.baseURL + request.path)
                    RequestMethod.DELETE ->
                        client.delete(
                            environment.baseURL + request.path,
                        )

                    RequestMethod.PATCH ->
                        client.patch(
                            environment.baseURL + request.path,
                        )

                    RequestMethod.HEAD ->
                        client.head(
                            environment.baseURL + request.path,
                        )
                }

            if (response.status.isSuccess()) {
                val body = response.bodyAsText()
                println("MKE body $body")

                LogUtils.logLongString("MKE :RESPONSE", body)
                val result = json.decodeFromString<NetworkResponse>(request.responseType(), body)
                println("MKE result---> $result")
                return Success(result)
            } else {
                return Error(Exception("Request failed"))
            }
//        } catch (e: Exception) {
//            return Error(e)
//        }
        } catch (e: IOException) {
            // Catches connectivity issues (no internet, timeout, host unresolved)
            return Error(e)
        } catch (e: ResponseException) {
            // Catches Ktor-specific HTTP status exceptions (if expectSuccess is enabled in Ktor)
            return Error(e)
        } catch (e: SerializationException) {
            // Catches JSON parsing/mapping failures
            return Error(e)
        }
    }
}

object LogUtils {
    private const val MAX_LOG_SIZE = 4000

    fun logLongString(
        tag: String?,
        message: String,
    ) {
        for (i in 0..message.length / MAX_LOG_SIZE) {
            val start = i * MAX_LOG_SIZE
            var end = (i + 1) * MAX_LOG_SIZE
            end = min(end, message.length)

            // Only log if there's content to avoid empty lines at the end
            if (start < end) {
                Log.d(tag, message.substring(start, end))
            }
        }
    }
}
