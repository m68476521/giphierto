package com.m68476521.networking.request

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
sealed interface Request<T> {
    val path: String
    val method: RequestMethod

    // This allow us to generically serialize what we expect
//    val serializer: KSerializer<T>

    fun <T: NetworkResponse> responseType(): KSerializer<T>
}

@Serializable
sealed interface NetworkResponse

enum class RequestMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
}
