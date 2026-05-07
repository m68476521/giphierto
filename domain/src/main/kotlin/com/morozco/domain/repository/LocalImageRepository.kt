package com.morozco.domain.repository

import com.morozco.core.model.Image
import kotlinx.coroutines.flow.Flow

interface LocalImageRepository {
    fun getAll(): Flow<List<Image>>
    suspend fun insert(image: Image)
    suspend fun deleteById(id: String)
    suspend fun imageById(id: String): Image?
}
