package com.m68476521.giphiertwo.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m68476521.giphiertwo.api.Image
import com.m68476521.giphiertwo.api.MainRepository
import com.m68476521.giphiertwo.home.SearchPaginationSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val mainRepository: MainRepository,
    ) : ViewModel() {
        fun searchByWord(word: String): Flow<PagingData<Image>> {
            searchFlow2 =
                Pager(
                    PagingConfig(pageSize = 25),
                ) {
                    SearchPaginationSource(word, mainRepository)
                }.flow.cachedIn(viewModelScope)
            return searchFlow2
        }

        lateinit var searchFlow2: Flow<PagingData<Image>>

        fun isInitialized(): Boolean = this::searchFlow2.isInitialized
    }
