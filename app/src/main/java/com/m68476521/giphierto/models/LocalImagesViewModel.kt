package com.m68476521.giphierto.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.m68476521.giphierto.data.AppDatabase
import com.m68476521.giphierto.data.Image
import com.m68476521.giphierto.data.ImageDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocalImagesViewModel(application: Application) : AndroidViewModel(application) {
    private val imagesDao: ImageDao = AppDatabase.getDatabase(application).imageDao()

    var images: List<Image> = emptyList()

    init {
        GlobalScope.launch {
            images = imagesDao.getAll()
        }
    }

    suspend fun insert(image: Image) {
        imagesDao.insert(image)
    }

    suspend fun deleteById(id: String) {
        imagesDao.deleteById(id)
    }

    suspend fun imageById(id: String): Image? {
        return imagesDao.imageById(id)
    }
}