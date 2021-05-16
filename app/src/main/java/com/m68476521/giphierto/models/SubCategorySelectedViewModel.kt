package com.m68476521.giphierto.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.m68476521.giphierto.api.MainRepository
import com.m68476521.giphierto.categories.SubcategoryPaginationSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubCategorySelectedViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    var subCategorySelected = ""
    val subCategoryFlow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 25)
    ) {
        SubcategoryPaginationSource(subCategorySelected, mainRepository)
    }.flow.cachedIn(viewModelScope)
}
