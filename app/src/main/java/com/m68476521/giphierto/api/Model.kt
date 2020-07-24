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
    val fixedHeightSmall: ImageSmall,
    val original: ImageOriginal,
    @SerializedName("fixed_height_small_still")
    val fixedHeightSmallStill: ImageSmall,
    @SerializedName("fixed_height_downsampled")
    val fixedHeightDownsampled: ImageSmall,
    @SerializedName("downsized_still")
    val downsizedStill: ImageSmall,
    @SerializedName("fixed_height_still")
    val fixedHeightStill: ImageSmall,
    @SerializedName("downsized_medium")
    val downsizedMedium: ImageSmall,
    @SerializedName("preview_webp")
    val previewWebp: ImageSmall,
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: ImageSmall,
    @SerializedName("fixed_width_small")
    val fixedWidthSmall: ImageSmall,
    @SerializedName("preview_gif")
    val previewGif: ImageSmall
)

data class ImageOriginal(
    val url: String
)

data class ImageSmall(
    val url: String?
)

enum class Rating(val rating: String) {
    G("G"),
    P("P"),
    PG_13("PG-13"),
    R("R")
}
