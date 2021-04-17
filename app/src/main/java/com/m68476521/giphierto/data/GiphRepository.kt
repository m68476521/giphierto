package com.m68476521.giphierto.data

import kotlinx.coroutines.flow.Flow

class GiphRepository(giphDao: ImageDao) {
    val allFavorites: Flow<List<Image>> = giphDao.getAll()
}
