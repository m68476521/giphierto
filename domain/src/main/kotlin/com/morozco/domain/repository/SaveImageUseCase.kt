package com.morozco.domain.repository

import com.morozco.core.model.Image
import kotlinx.coroutines.flow.Flow

class SaveImageUseCase(
    private val repository: LocalImageRepository
) {
    fun getAll(): Flow<List<Image>> = repository.getAll()

    suspend fun insert(image: Image) = repository.insert(image)

    suspend fun deleteById(id: String) = repository.deleteById(id)

    suspend fun imageById(id: String): Image? = repository.imageById(id)
}
