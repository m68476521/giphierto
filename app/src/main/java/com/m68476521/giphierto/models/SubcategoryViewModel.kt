package com.m68476521.giphierto.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.categories.SubcategoryPaginationSource

class SubcategoryViewModel : ViewModel() {
    var category = ""
    val subcategoryflow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 25)
    ) {
        SubcategoryPaginationSource(category, GiphyManager.giphyApi)
    }.flow.cachedIn(viewModelScope)
}
