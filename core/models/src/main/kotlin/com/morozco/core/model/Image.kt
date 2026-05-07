package com.morozco.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val type: String,
    val id: String,
    val url: String,
    @SerialName("embed_url")
    val embedUrl: String ?=null,
    val title: String,
    val images: SubImage? = null,
)


@Serializable
data class Pagination(
    @SerialName("total_count")
    val totalCount: Int ? = null,
    @SerialName("count")
    val count: Int,
    val offset: Int? = null,
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
data class SubCategoryData(

    @SerialName("name_encoded")
    val nameEncoded: String? = null,
    val name: String?,
    val gif: Gif?,
)

@Serializable
data class Data(
    val name: String,
    @SerialName("name_encoded")
    val nameEncoded: String? = null,
    val subcategories: List<Subcategories>?,
    val gif: Gif,
)

@Serializable
data class Subcategories(
    val name: String,
    @SerialName("name_encoded")
    val nameEncoded: String? = null, //1
)

@Serializable
data class Gif(
    @SerialName("type")
    val type: String,
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("bitly_gif_url")
    val bitlyGifUrl: String ?= null,
    @SerialName("bitly_url")
    val bitlyUrl: String?= null,
    @SerialName("embed_url")
    val embedUrl: String?= null,
    @SerialName("username")
    val username: String,
    @SerialName("source")
    val source: String,
    @SerialName("title")
    val title: String,
    @SerialName("rating")
    val rating: String,
    @SerialName("content_url")
    val contentUrl: String?= null,
    @SerialName("tags")
    val tags: List<String>?= null,
    @SerialName("featured_tags")
    val featuredTags: List<String> = emptyList(),
    @SerialName("source_tld")
    val sourceTld: String?= null,
    @SerialName("source_post_url")
    val sourcePostUrl: String?= null,
    @SerialName("is_sticker")
    val isSticker: Int ?= null,
    @SerialName("import_datetime")
    val importDatetime: String?= null,
    @SerialName("trending_datetime")
    val trendingDatetime: String?= null,
    @SerialName("create_datetime")
    val createDatetime: String?= null,
    @SerialName("update_datetime")
    val updateDatetime: String?= null,
    @SerialName("images")
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
    @SerialName("mp4_size")
    val mp4Size: String ? = null,
    val mp4: String ? = null,
    @SerialName("webp_size")
    val webpSize : String ? = null,
    val webp : String ? = null,
)
