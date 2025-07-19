package com.m68476521.giphiertwo.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.m68476521.giphiertwo.data.GiphRepository
import com.m68476521.giphiertwo.data.Image

class FavoritesViewModel(
    repository: GiphRepository,
) : ViewModel() {
    val favorites: LiveData<List<Image>> = repository.allFavorites.asLiveData()
}

class FavoriteViewModelFactory(
    private val repository: GiphRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
