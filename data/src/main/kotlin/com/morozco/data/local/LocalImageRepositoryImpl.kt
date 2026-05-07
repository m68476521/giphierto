package com.morozco.data.local

import com.morozco.core.model.Image
import com.morozco.core.model.ImageOriginal
import com.morozco.core.model.SubImage
import com.morozco.core.model.ImageData
import com.morozco.data.local.entities.ImageEntity
import com.morozco.domain.repository.LocalImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalImageRepositoryImpl(
    private val imageDao: ImageDao
) : LocalImageRepository {

    override fun getAll(): Flow<List<Image>> {
        return imageDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insert(image: Image) {
        imageDao.insert(image.toEntity())
    }

    override suspend fun deleteById(id: String) {
        imageDao.deleteById(id)
    }

    override suspend fun imageById(id: String): Image? {
        return imageDao.imageById(id)?.toDomain()
    }

    private fun ImageEntity.toDomain(): Image {
        return Image(
            id = uid,
            type = "", 
            url = originalUrl,
            title = title,
            images = SubImage(
                original = ImageOriginal(url = originalUrl),
                fixedHeightDownsampled = ImageData(url = fixedHeightDownsampled)
            )
        )
    }

    private fun Image.toEntity(): ImageEntity {
        return ImageEntity(
            uid = id,
            originalUrl = images?.original?.url.orEmpty(),
            fixedHeightDownsampled = images?.fixedHeightDownsampled?.url.orEmpty(),
            title = title
        )
    }
}
