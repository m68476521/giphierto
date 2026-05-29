package com.m68476521.networking.request

import com.morozco.core.model.Image
import com.morozco.core.model.Pagination
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetRelated(
    val gifId: String,
    val limit: Int,
) : Request<SubCategoryDataResponse> {
    override val path: String = "v1/gifs/related"

    override val method = RequestMethod.GET

    override val parameters: Map<String, String>
        get() = mapOf(
            "gif_id" to gifId,
            "limit" to limit.toString(),
        )

    override fun <T : NetworkResponse> responseType(): KSerializer<T> {
        @Suppress("UNCHECKED_CAST")
        return RelatedData.serializer() as KSerializer<T>
    }

}

@Serializable
data class RelatedData(
    val pagination: Pagination,
    val data: List<Image>,
) : NetworkResponse
