package com.m68476521.giphiertwo.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GiphRepository
    @Inject
    constructor(
        imageDao: ImageDao,
    ) {
        val allFavorites: Flow<List<Image>> = imageDao.getAll()
    }
