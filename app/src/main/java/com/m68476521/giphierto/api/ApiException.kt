package com.m68476521.giphierto.api

import retrofit2.HttpException
import java.lang.RuntimeException

class ApiException(httpException: HttpException) : RuntimeException(httpException) {
    val statusCode: Int = httpException.code()
    val error: String = httpException.message()

    override fun printStackTrace() {
        this.cause?.printStackTrace()
    }

    companion object {
        const val ACCEPTED = 202
        const val BAD_GATEWAY = 502
        const val BAD_METHOD = 405
        const val BAD_REQUEST = 400
        const val CLIENT_TIMEOUT = 408
        const val CONFLICT = 409
        const val CREATED = 201
        const val ENTITY_TOO_LARGE = 413
        const val FORBIDDEN = 403
        const val GATEWAY_TIMEOUT = 504
        const val GONE = 410
        const val INTERNAL_ERROR = 500
        const val LENGTH_REQUIRED = 411
        const val MOVED_PERM = 301
        const val MOVED_TEMP = 302
        const val MULT_CHOICE = 300
        const val NOT_ACCEPTABLE = 406
        const val NOT_AUTHORITATIVE = 203
        const val NOT_FOUND = 404
        const val NOT_IMPLEMENTED = 501
        const val NOT_MODIFIED = 304
        const val NO_CONTENT = 204
        const val OK = 200
        const val PARTIAL = 206
        const val PAYMENT_REQUIRED = 402
        const val PRECON_FAILED = 412
        const val PROXY_AUTH = 407
        const val REQ_TOO_LONG = 414
        const val RESET = 205
        const val SEE_OTHER = 303
        const val UNAUTHORIZED = 401
        const val TOO_MANY_REQUESTS = 429
    }
}
