package com.m68476521.giphierto.api

import com.google.gson.annotations.SerializedName

data class Image(
    val type: String,
    val id: String,
    val url: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    val title: String,
    val images: SubImage
)

data class ImageResponse(
    val data: List<Image>,
    val pagination: Pagination
)

data class Pagination(
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int,
    val offset: Int
)

data class SubImage(
    @SerializedName("fixed_height_small")
    val fixedHeightSmall: ImageSmall
)

data class ImageSmall(
    val url: String
)

enum class Rating(val rating: String) {
    G("G"),
    P("P"),
    PG_13("PG-13"),
    R("R")
}
