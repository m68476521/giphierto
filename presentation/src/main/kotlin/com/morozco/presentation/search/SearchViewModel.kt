package com.morozco.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.morozco.core.model.Image
import com.morozco.core.model.Screen
import com.morozco.presentation.search.domain.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val PAGINATION_SIZE = 20
@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val useCase: SearchUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(), SearchPresentation {
    override fun navigateBack() {
//        TODO("Not yet implemented")
    }

    private val route = savedStateHandle.toRoute<Screen.Search>()

    private val _uiState = MutableStateFlow(SearchUIState(
        listOfImages = Pager(
            config = PagingConfig(pageSize = PAGINATION_SIZE),
            pagingSourceFactory = {
                useCase.pagingSourceForSearch(
                    search = route.word,
                    offset = 0,
                    pagination = 0,
                    limit = PAGINATION_SIZE
                )
            }
        ).flow.cachedIn(viewModelScope)
    ))

    override val UIState: StateFlow<SearchUIState> = _uiState

    override fun updateSelectedItem(item: Image) {
        _uiState.update {
            it.copy(
                currentItemSelected = item,
            )
        }
    }

    override fun clearSelectedItem() {
        _uiState.update {
            it.copy(
                currentItemSelected = null,
            )
        }
    }



//    init {
//        viewModelScope.launch {
//            val response = useCase.search2()
//
//            when (response) {
//                is SearchResult.SearchSuccess -> {
//                    println("MKE on success $response")
////                    _UIstate.update {
////                        it.copy(listOfImages = response.events.data)
////                    }
//                }
//                is SearchResult.SearchFailure -> {
//                    println("MKE on FAIL $response")
//                }
//
//                is SearchResult.EmptyData -> {
//                    println("MKE on EMpty $response")
//                }
//            }
//        }
//    }

}
