package com.morozco.core.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Dashboard : Screen()

    @Serializable
    data object Search : Screen()

    @Serializable
    data object Categories : Screen()

    @Serializable
    data class SubCategories(val subcategory: String) : Screen()
}
