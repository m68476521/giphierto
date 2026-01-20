package com.m68476521.giphiertwo.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Dashboard : Screen()
}