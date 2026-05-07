package com.morozco.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.morozco.data.local.entities.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM images")
    fun getAll(): Flow<List<ImageEntity>>

    @Query("DELETE FROM images")
    suspend fun deleteAll()

    @Query("DELETE FROM images WHERE uid = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM images WHERE uid = :id LIMIT 1")
    suspend fun imageById(id: String): ImageEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: ImageEntity): Long
}
