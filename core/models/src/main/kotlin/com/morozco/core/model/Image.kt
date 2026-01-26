package com.morozco.core.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val type: String,
    val id: String,
    val url: String,
    @SerializedName("embed_url")
    val embedUrl: String ?=null,
    val title: String,
    val images: SubImage,
)


@Serializable
data class Pagination(
    @SerializedName("total_count")
    val totalCount: Int ? = null,
    val count: Int,
    val offset: Int,
)

@Serializable
data class SubImage(
    @SerializedName("fixed_height_small")
    val fixedHeightSmall: ImageSmall ? = null,
    val original: ImageOriginal,
    @SerializedName("fixed_height_small_still")
    val fixedHeightSmallStill: ImageSmall ? = null,
    @SerialName("fixed_height_downsampled")
    val fixedHeightDownsampled: ImageData ? = null,
    @SerializedName("downsized_still")
    val downsizedStill: ImageSmall ?= null,
    @SerializedName("fixed_height_still")
    val fixedHeightStill: ImageSmall ?= null,
    @SerializedName("downsized_medium")
    val downsizedMedium: ImageSmall ?= null,
    @SerializedName("preview_webp")
    val previewWebp: ImageSmall ?=null,
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: ImageSmall ? = null,
    @SerializedName("fixed_width_small")
    val fixedWidthSmall: ImageSmall ?= null,
    @SerializedName("preview_gif")
    val previewGif: ImageSmall ?= null,
    @SerializedName("fixed_height")
    val fixedHeight: ImageOriginal ?= null,
)

@Serializable
data class ImageOriginal(
    val url: String,
)

@Serializable
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

@Serializable
data class ImageData(
    val height: String ? = null,
    val width: String ? = null,
    val size: String ? = null,
    val url: String ? = null,
    @SerializedName("mp4_size")
    val mp4Size: String ? = null,
    val mp4: String ? = null,
    @SerializedName("webp_size")
    val webpSize : String ? = null,
    val webp : String ? = null,
)
