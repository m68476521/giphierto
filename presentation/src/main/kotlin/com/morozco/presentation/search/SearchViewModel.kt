package com.morozco.presentation.search

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.morozco.presentation.search.domain.SearchResult
import com.morozco.presentation.search.domain.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGINATION_SIZE = 20
@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val useCase: SearchUseCase,
): ViewModel(), SearchPresentation {
    override fun navigateBack() {
//        TODO("Not yet implemented")
    }

    private val _UIstate = MutableStateFlow(SearchUIState(
        listOfImages = Pager(
            config = PagingConfig(pageSize = PAGINATION_SIZE),
            pagingSourceFactory = {
                useCase.pagingSourceForSearch(
                    search = "Friends",
                    offset = PAGINATION_SIZE,
                    pagination = PAGINATION_SIZE,
                    limit = PAGINATION_SIZE
                )
            }
        ).flow.cachedIn(viewModelScope)
    ))

    override val UIState: StateFlow<SearchUIState> = _UIstate


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
