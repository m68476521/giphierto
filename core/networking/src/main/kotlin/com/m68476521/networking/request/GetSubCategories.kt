package com.m68476521.networking.request

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
data class GetSubCategories(
    val category: String,
    val offset: Int,
    val limit: Int,
) : Request<SubCategoryDataResponse> {
    override val path: String = "/v1/gifs/categories/${category}"

    override val method = RequestMethod.GET

    override val parameters: Map<String, String>
        get() = mapOf(
            "offset" to offset.toString(),
            "limit" to limit.toString(),
        )

    override fun <T : NetworkResponse> responseType(): KSerializer<T> {
        @Suppress("UNCHECKED_CAST")
        return SubCategoryDataResponse.serializer() as KSerializer<T>
    }

}
