package com.morozco.core.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Dashboard : Screen()

    @Serializable
    data class Search(val word: String) : Screen()

    @Serializable
    data object Categories : Screen()

    @Serializable
    data class SubCategories(val subcategory: String) : Screen()


    @Serializable
    data object Favorites: Screen()
}
