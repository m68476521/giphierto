package com.m68476521.giphiertwo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "originalUrl")
    val originalUrl: String,
    @ColumnInfo(name = "fixedHeightDownsampled")
    val fixedHeightDownsampled: String,
    @ColumnInfo(name = "title")
    val title: String,
)
