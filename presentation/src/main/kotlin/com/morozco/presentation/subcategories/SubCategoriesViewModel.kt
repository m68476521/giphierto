package com.morozco.presentation.subcategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.morozco.presentation.categories.domain.CategoriesUseCase
import com.morozco.presentation.categories.domain.GetSubcategoriesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubCategoriesViewModel
@Inject
constructor(
    private val useCase: CategoriesUseCase,
) : ViewModel(), SubCategoriesPresentation {

    init {
        viewModelScope.launch {
            when (val result = useCase.getSubcategories("adjectives")) {
                is GetSubcategoriesResult.FetchingSuccess -> {
                    println("MKE result $result")
                }

                is GetSubcategoriesResult.EmptyData -> {
                    println("MKE empty $result")

                }

                else -> {
                    println("MKE FAILED $result")
                }
            }
        }
    }

    private val _state = MutableStateFlow(
        SubCategoriesUIState(
            listOfSubCategories = Pager(
                config = PagingConfig(pageSize = 25),
                pagingSourceFactory = {
                    useCase.pagingSourceForSubcategories(
                        category = "actions",
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
