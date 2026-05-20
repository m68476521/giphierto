package com.morozco.presentation.detail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.morozco.core.model.Image
import com.morozco.core.model.Screen
import com.morozco.domain.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
    private val navigator: Navigator,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DetailPresentation {

    private val route = savedStateHandle.toRoute<Screen.DetailItem>(
        typeMap = mapOf(typeOf<Image>() to Image.NavigationType)
    )
    private val _state = MutableStateFlow(DetailUIState(image = route.image))

    override val state: StateFlow<DetailUIState> = _state

    override suspend fun goBack() {
        navigator.navigateBack()
    }
}