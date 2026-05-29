package com.morozco.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.morozco.core.model.Image
import com.morozco.core.model.Screen
import com.morozco.domain.navigation.Navigator
import com.morozco.presentation.dashboard.domain.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val useCase: HomeUseCase,
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DetailPresentation {

    private val route = savedStateHandle.toRoute<Screen.DetailItem>(
        typeMap = mapOf(typeOf<Image>() to Image.NavigationType)
    )
    private val _uiState = MutableStateFlow(DetailUIState(image = route.image))

    override val uiState: StateFlow<DetailUIState> = _uiState

    init {
        viewModelScope.launch {
            when (val result = useCase.getRelatedGifts(
                giftId = route.image.id,
                limit = 10,
            )) {
                is HomeUseCase.GetRelatedResult.FetchingSuccess -> {
                    _uiState.update {
                        it.copy(
                            relatedGiftList = result.related.data,
                        )
                    }
                }

                is HomeUseCase.GetRelatedResult.FetchingFailed -> {
                    println("failed ${result}")
                }
            }
        }
    }

    override suspend fun goBack() {
        navigator.navigateBack()
    }
}
