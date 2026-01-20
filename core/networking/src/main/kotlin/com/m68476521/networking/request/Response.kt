package com.m68476521.networking.request

class Response<T>(
    // CHALLENGE-SHORTCUT: This is here because I wanted to log it and use it in a few cases, but
    // it is overkill for the challenge.
    val responseCode: Int,
    val result: Result<T>
)
