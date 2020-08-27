package com.m68476521.giphierto.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Image(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "image") val image: String
)