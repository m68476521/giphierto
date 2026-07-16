package com.m68476521.networking.request

sealed class NetworkResult<out T : Any> {
    class Success<out T : Any>(
        val resultValue: T,
    ) : NetworkResult<T>()

    class Error<out T : Any>(
        val error: Throwable?,
    ) : NetworkResult<T>()

    fun getOrNull(): T? =
        when (this) {
            is Success -> resultValue
            is Error -> null
        }

    fun getExceptionOrNull(): Throwable? =
        when (this) {
            is Success -> null
            is Error -> error
        }
}

fun <T : Any> NetworkResult<T>.toResult(): Result<T> =
    when (this) {
        is NetworkResult.Success -> Result.success(resultValue)
        is NetworkResult.Error -> Result.failure(error ?: Throwable("Unknown error"))
    }
