package com.m68476521.giphiertwo.models

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubCategorySelectedViewModel
    @Inject
    constructor(
//        private val mainRepository: MainRepository,
    ) : ViewModel() {
        var subCategorySelected = ""
//        val subCategoryFlow =
//            Pager(
//                // Configure how data is loaded by passing additional properties to
//                // PagingConfig, such as prefetchDistance.
//                PagingConfig(pageSize = 25),
//            ) {
//                SubcategoryPaginationSource(subCategorySelected, mainRepository)
//            }.flow.cachedIn(viewModelScope)
    }
