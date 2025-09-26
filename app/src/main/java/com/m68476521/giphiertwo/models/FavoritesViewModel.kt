package com.m68476521.giphiertwo.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m68476521.giphiertwo.data.GiphRepository
import com.m68476521.giphiertwo.data.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel
    @Inject
    constructor(
        private val repository: GiphRepository,
    ) : ViewModel() {
        private val _myFavorites = MutableStateFlow<List<Image>>(emptyList())

        val myFavorites: StateFlow<List<Image>> = _myFavorites

        private val _state = MutableStateFlow(FavoritesViewState())

        val state: StateFlow<FavoritesViewState> = _state

        fun handleIntent(intent: FavoriteIntent) {
            viewModelScope.launch {
                when (intent) {
                    is FavoriteIntent.SelectItem -> {
                        _state.value =
                            _state.value.copy(
                                currentItemSelected = intent.item,
                            )
                    }

                    is FavoriteIntent.ClearItemSelected -> {
                        _state.value =
                            _state.value.copy(
                                currentItemSelected = null,
                            )
                    }
                }
            }
        }

        fun getData() {
            viewModelScope.launch {
                repository.allFavorites.collect {
                    _myFavorites.value = it
                }
            }
        }
    }

// TODO: On this model, I need to be sure that I need to save the whole image item, or at least, 2 URLs
// 1 to show in the grid and another the actual URL for sharing

data class FavoritesViewState(
    val currentItemSelected: Image? = null,
)

sealed class FavoriteIntent {
    data class SelectItem(
        val item: Image,
    ) : FavoriteIntent()

    data object ClearItemSelected : FavoriteIntent()
}
