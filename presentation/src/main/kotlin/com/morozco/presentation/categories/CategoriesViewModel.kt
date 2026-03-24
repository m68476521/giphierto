package com.morozco.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morozco.core.model.Subcategories
import com.morozco.domain.navigation.Navigator
import com.morozco.presentation.categories.domain.CategoriesUseCase
import com.morozco.presentation.dashboard.domain.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel
@Inject
constructor(
    private val useCase: CategoriesUseCase,
    private val navigator: Navigator
) : ViewModel(), CategoriesPresentation {

    private val _state = MutableStateFlow(CategoriesUIState())

    override val state: StateFlow<CategoriesUIState> = _state

    init {
        viewModelScope.launch {

            when (val response = useCase.getCategories()) {
                is HomeUseCase.GetCategoriesResult.FetchingSuccess -> {
                    _state.update {
                        it.copy(
                            listOfCategories = response.events.data
                        )
                    }
                }

                is HomeUseCase.GetCategoriesResult.EmptyData -> {

                }

                is HomeUseCase.GetCategoriesResult.Failure -> {

                }
            }
        }
    }

    override fun navigateToSubCategories(subcategory: String) {
        navigator.navigateToSubCategories(subcategory)
    }

}