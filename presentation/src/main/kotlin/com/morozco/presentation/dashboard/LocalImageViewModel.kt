package com.morozco.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morozco.core.model.Image
import com.morozco.domain.repository.SaveImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LocalImagesViewModel
@Inject
constructor(
    private val saveImageUseCase: SaveImageUseCase,
) : ViewModel(), LocalPresentation {

    override val state: StateFlow<LocalUIState> = saveImageUseCase.getAll()
        .map { images -> LocalUIState(images = images) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LocalUIState()
        )

    override fun insert(image: Image) {
        viewModelScope.launch {
            saveImageUseCase.insert(image)
        }
    }

    override fun delete(id: String) {
        viewModelScope.launch {
            saveImageUseCase.deleteById(id)
        }
    }

    override fun getImage(id: String) {
        viewModelScope.launch {
            val image = saveImageUseCase.imageById(id)
            // Handle image retrieved if necessary, 
            // maybe update a specific state for "current selected local image"
        }
    }
}
