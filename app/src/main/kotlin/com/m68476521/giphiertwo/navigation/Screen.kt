package com.m68476521.giphiertwo.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Dashboard : Screen()

    @Serializable
    data object Search : Screen()

    @Serializable
    data object Categories : Screen()
}
