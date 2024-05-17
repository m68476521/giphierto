package com.m68476521.giphiertwo.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.m68476521.giphiertwo.data.AppDatabase
import com.m68476521.giphiertwo.data.Image
import com.m68476521.giphiertwo.data.ImageDao

class LocalImagesViewModel(application: Application) : AndroidViewModel(application) {
    val imagesDao: ImageDao = AppDatabase.getDatabase(application).imageDao()

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
