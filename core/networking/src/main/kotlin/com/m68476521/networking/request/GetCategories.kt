package com.m68476521.networking.request

import com.morozco.core.model.Data
import com.morozco.core.model.Image
import com.morozco.core.model.Pagination
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

@Serializable
data object GetCategories2: Request<ImageResponse> {
    override val path: String = "/v1/gifs/categories"

    override val method = RequestMethod.GET

    override fun <T : NetworkResponse> responseType(): KSerializer<T> {
        @Suppress("UNCHECKED_CAST")
        return CategoryData.serializer() as KSerializer<T>
    }

    //    override val serializer: KSerializer<ImageResponse>
//        get() = ImageResponse.serializer()
//    override fun <T : Response> responseType(): KSerializer<T> {
//        @Suppress("UNCHECKED_CAST")
//        return ImageResponse.serializer() as KSerializer<T>
//
//    }

}


@Serializable
data class CategoryData(
    val pagination: Pagination ?= null,
    val data: List<Data>,
) : NetworkResponse

