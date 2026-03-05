package com.m68476521.networking.request

import com.morozco.core.model.Image
import com.morozco.core.model.Pagination
import com.morozco.core.model.Rating
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchImages(
    val rating: String,
    val offset: Int,
    val q: String,
    val limit: Int,
    val lang: String = "en",
    @SerialName("random_id")
    val randomId:  String,
    @SerialName("country_code")
    val countryCode: String = "US",
    @SerialName("remove_low_contrast")
    val removeLowContrast: Boolean = true,
) : Request<ImageResponse> {
    override val path: String = "/v1/gifs/search"

    override val method = RequestMethod.GET

    override val headers: List<Pair<String, String>>
       = listOf(
           "q" to q,
        "offset" to offset.toString(),
        "limit" to limit.toString(),
        "rating" to rating,
        "lang" to lang
       )

    override val parameters: Map<String, String>
        get() = mapOf(
            "q" to q,
            "offset" to offset.toString(),
            "limit" to limit.toString(),
            "rating" to rating,
            "lang" to lang
        )

    override fun <T : NetworkResponse> responseType(): KSerializer<T> {
        @Suppress("UNCHECKED_CAST")
        return ImageResponse.serializer() as KSerializer<T>
    }


}
