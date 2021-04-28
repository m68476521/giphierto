package com.m68476521.giphierto.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.api.Image
import com.m68476521.giphierto.home.SearchPaginationSource
import kotlinx.coroutines.flow.Flow

class SearchViewModel : ViewModel() {

    fun searchByWord(word: String): Flow<PagingData<Image>> {
        searchFlow2 = Pager(
            PagingConfig(pageSize = 25)
        ) {
            SearchPaginationSource(word, GiphyManager.giphyApi)
        }.flow.cachedIn(viewModelScope)
        return searchFlow2
    }

    lateinit var searchFlow2: Flow<PagingData<Image>>

    fun isInitialized(): Boolean {
        return this::searchFlow2.isInitialized
    }
}
