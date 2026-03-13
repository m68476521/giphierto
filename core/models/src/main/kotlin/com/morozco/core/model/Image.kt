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
    @SerialName("total_count")
    val totalCount: Int ? = null,
    @SerialName("count")
    val count: Int,
    val offset: Int,
)

@Serializable
data class SubImage(
    @SerialName("fixed_height_small")
    val fixedHeightSmall: ImageSmall ? = null,
    val original: ImageOriginal,
    @SerialName("fixed_height_small_still")
    val fixedHeightSmallStill: ImageSmall ? = null,
    @SerialName("fixed_height_downsampled")
    val fixedHeightDownsampled: ImageData ? = null,
    @SerialName("downsized_still")
    val downsizedStill: ImageSmall ?= null,
    @SerialName("fixed_height_still")
    val fixedHeightStill: ImageSmall ?= null,
    @SerialName("downsized_medium")
    val downsizedMedium: ImageSmall ?= null,
    @SerialName("preview_webp")
    val previewWebp: ImageSmall ?=null,
    @SerialName("fixed_width_downsampled")
    val fixedWidthDownsampled: ImageSmall ? = null,
    @SerialName("fixed_width_small")
    val fixedWidthSmall: ImageSmall ?= null,
    @SerialName("preview_gif")
    val previewGif: ImageSmall ?= null,
    @SerialName("fixed_height")
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

//@Serializable
//data class CategoryData(
//    val pagination: Pagination,
//    val data: List<Data>,
//)


@Serializable
data class Data(
    val name: String,
    @SerializedName("name_encoded")
    val nameEncoded: String? = null,
    val subcategories: List<Subcategories>?,
    val gif: Gif,
)

@Serializable
data class Subcategories(
    val name: String,
    @SerializedName("name_encoded")
    val nameEncoded: String? = null, //1
)

@Serializable
data class Gif(
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String ?= null,
    @SerializedName("bitly_url")
    val bitlyUrl: String?= null,
    @SerializedName("embed_url")
    val embedUrl: String?= null,
    @SerializedName("username")
    val username: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("content_url")
    val contentUrl: String?= null,
    @SerializedName("tags")
    val tags: List<String>?= null,
    @SerializedName("featured_tags")
    val featuredTags: List<String> = emptyList(),
    @SerializedName("source_tld")
    val sourceTld: String?= null,
    @SerializedName("source_post_url")
    val sourcePostUrl: String?= null,
    @SerializedName("is_sticker")
    val isSticker: Int ?= null,
    @SerializedName("import_datetime")
    val importDatetime: String?= null,
    @SerializedName("trending_datetime")
    val trendingDatetime: String?= null,
    @SerializedName("create_datetime")
    val createDatetime: String?= null,
    @SerializedName("update_datetime")
    val updateDatetime: String?= null,
    @SerializedName("images")
    val images: Images,
)

@Serializable
data class Images(
    @SerialName("fixed_height_small_still")
    val fixedHeightSmallStill: ImageSmall ?= null,
    val original: ImageSmall?= null,
    @SerialName("fixed_height_downsampled")
    val fixedHeightDownSampled: ImageSmall?= null,
    @SerialName("downsized_still")
    val downsizedStill: ImageSmall?= null,
    @SerialName("fixed_height_still")
    val fixedHeightStill: ImageSmall?= null,
    @SerialName("downsized_medium")
    val downsizedMedium: ImageSmall?= null,
    @SerialName("preview_webp")
    val previewWebp: ImageSmall?= null,
    @SerialName("fixed_height_small")
    val fixedHeightSmall: ImageSmall?= null,
    @SerialName("fixed_width_downsampled")
    val fixedWidthDownSampled: ImageSmall?= null,
    @SerialName("fixed_width_small")
    val fixedWidthSmall: ImageSmall?= null,
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
