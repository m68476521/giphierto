package com.m68476521.networking.request

import kotlinx.serialization.KSerializer

sealed interface Request<T> {
    val path: String
    val method: RequestMethod

    // This allow us to generically serialize what we expect
    val serializer: KSerializer<T>
}

enum class RequestMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
}
