package com.m68476521.giphiertwo.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.m68476521.giphiertwo.data.AppDatabase
import com.m68476521.giphiertwo.data.Image
import com.m68476521.giphiertwo.data.ImageDao
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LocalImagesViewModel
@Inject
constructor(
    application: Application
) : AndroidViewModel(application) {
    val imagesDao: ImageDao = AppDatabase.getDatabase(application).imageDao()

    private val _state = MutableStateFlow(FavoriteViewState())
    val state: StateFlow<FavoriteViewState> = _state

    fun insert(image: Image) {
        viewModelScope.launch {
            imagesDao.insert(image)
            imageById(image.uid)
        }
    }

    fun deleteById(id: String) {
        viewModelScope.launch {
            imagesDao.deleteById(id)
            imageById(id)
        }
    }

    fun imageById(id: String) {
        viewModelScope.launch {
            val result = imagesDao.imageById(id)
            _state.value = _state.value.copy(isFavorite = result != null)
        }
    }
}

data class FavoriteViewState(
    val isFavorite: Boolean = false,
)
