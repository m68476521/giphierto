package com.m68476521.giphierto.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM images")
    fun getAll(): Flow<List<Image>>

    @Query("DELETE FROM images")
    suspend fun deleteAll()

    @Query("DELETE FROM images WHERE uid = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM images WHERE uid = :id LIMIT 1")
    suspend fun imageById(id: String): Image?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(director: Image): Long
}
