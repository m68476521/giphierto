package com.m68476521.giphiertwo.api

import com.google.gson.annotations.SerializedName

data class Image(
    val type: String,
    val id: String,
    val url: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    val title: String,
    val images: SubImage,
)

data class ImageResponse(
    val data: List<Image>,
    val pagination: Pagination,
)

data class Pagination(
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int,
    val offset: Int,
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
    val previewGif: ImageSmall,
    @SerializedName("fixed_height")
    val fixedHeight: ImageOriginal,
)

data class ImageOriginal(
    val url: String,
)

data class ImageSmall(
    val url: String?,
)

enum class Rating(
    val rating: String,
) {
    G("G"),
    P("P"),
    PG_13("PG-13"),
    R("R"),
}

data class CategoryData(
    val pagination: Pagination,
    val data: List<Data>,
)

data class Data(
    val name: String,
    @SerializedName("name_encoded")
    val nameEncoded: String,
    val subcategories: List<Subcategories>,
    val gif: Gif,
)

data class Subcategories(
    val name: String,
    @SerializedName("name_encoded")
    val nameEncoded: String,
)

data class Gif(
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("bitly_gif_url") val bitlyGifUrl: String,
    @SerializedName("bitly_url") val bitlyUrl: String,
    @SerializedName("embed_url") val embedUrl: String,
    @SerializedName("username") val username: String,
    @SerializedName("source") val source: String,
    @SerializedName("title") val title: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("content_url") val contentUrl: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("featured_tags") val featuredTags: List<String>,
    @SerializedName("source_tld") val sourceTld: String,
    @SerializedName("source_post_url") val sourcePostUrl: String,
    @SerializedName("is_sticker") val isSticker: Int,
    @SerializedName("import_datetime") val importDatetime: String,
    @SerializedName("trending_datetime") val trendingDatetime: String,
    @SerializedName("create_datetime") val createDatetime: String,
    @SerializedName("update_datetime") val updateDatetime: String,
    @SerializedName("images") val images: Images,
)

data class Images(
    @SerializedName("fixed_height_small_still") val fixedHeightSmallStill: ImageSmall,
    val original: ImageSmall,
    @SerializedName("fixed_height_downsampled") val fixedHeightDownSampled: ImageSmall,
    @SerializedName("downsized_still") val downsizedStill: ImageSmall,
    @SerializedName("fixed_height_still") val fixedHeightStill: ImageSmall,
    @SerializedName("downsized_medium") val downsizedMedium: ImageSmall,
    @SerializedName("preview_webp") val previewWebp: ImageSmall,
    @SerializedName("fixed_height_small") val fixedHeightSmall: ImageSmall,
    @SerializedName("fixed_width_downsampled") val fixedWidthDownSampled: ImageSmall,
    @SerializedName("fixed_width_small") val fixedWidthSmall: ImageSmall,
)
