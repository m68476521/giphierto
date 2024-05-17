package com.m68476521.giphiertwo.data

import kotlinx.coroutines.flow.Flow

class GiphRepository(giphDao: ImageDao) {
    val allFavorites: Flow<List<Image>> = giphDao.getAll()
}
