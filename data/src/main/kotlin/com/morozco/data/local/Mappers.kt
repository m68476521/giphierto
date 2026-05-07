package com.morozco.data.local

import com.morozco.core.model.Image
import com.morozco.data.local.entities.ImageEntity

fun Image.toEntity() = ImageEntity(
    uid = this.id,
    title = this.title,
    originalUrl = this.images?.original?.url.orEmpty(),
    // TODO check this one
    fixedHeightDownsampled = "",
)

fun ImageEntity.toDomain() = Image(
    id = this.uid,
    title = this.title,
    type = "",
    url = this.originalUrl,
)