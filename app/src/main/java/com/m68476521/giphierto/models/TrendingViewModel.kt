package com.m68476521.giphierto.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.home.TrendingPaginationSource

class TrendingViewModel : ViewModel() {
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 25)
    ) {
        TrendingPaginationSource(GiphyManager.giphyApi)
    }.flow.cachedIn(viewModelScope)
}
