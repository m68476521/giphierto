package com.m68476521.networking.request

sealed class Environment(val baseURL: String) {
    data object Dev : Environment("https://api.giphy.com")

    data object Prod : Environment("https://THE_URL_FOR_PROD_ENV_GOES_HERE")

    // This uses a special IP address to reference the computer the Android emulator is running on
    data object LocalEmulator : Environment("http://10.0.2.2:8080")

    data class Custom(val url: String) : Environment(url)

    companion object {
        fun fromString(value: String): Environment {
            return if (value.equals("dev", ignoreCase = true)) {
                Dev
            } else if (value.equals("prod", ignoreCase = true)) {
                Prod
            } else if (value.equals("local", ignoreCase = true)) {
                LocalEmulator
            } else {
                // CHALLENGE-SHORTCUT: On a real application this URLs should be defined as
                // part of the build configuration. For the Custom option we could create
                // a debug dashboard to set it from there.
                Custom("")
            }
        }
    }
}