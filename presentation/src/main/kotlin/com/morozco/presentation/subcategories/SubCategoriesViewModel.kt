package com.morozco.presentation.subcategories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.morozco.core.model.Screen
import com.morozco.presentation.categories.domain.CategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SubCategoriesViewModel
@Inject
constructor(
    private val useCase: CategoriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(), SubCategoriesPresentation {

    private val route = savedStateHandle.toRoute<Screen.SubCategories>()


    private val _state = MutableStateFlow(
        SubCategoriesUIState(
            listOfSubCategories = Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    val categoryName = route.subcategory
                    useCase.pagingSourceForSubcategories(
                        category = categoryName,
                        pagination = 0,
                        limit = 20
                    )
                }
            ).flow.cachedIn(viewModelScope)
        )
    )

    override val state: StateFlow<SubCategoriesUIState> = _state

    override fun navigateToNext() {

    }
}
