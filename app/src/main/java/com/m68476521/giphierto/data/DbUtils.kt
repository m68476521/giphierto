package com.m68476521.giphierto.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun rebuildDB(database: AppDatabase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            val movieDao: ImageDao = db.imageDao()
            movieDao.deleteAll()
        }
    }
}
