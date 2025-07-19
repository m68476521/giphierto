package com.m68476521.giphiertwo.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.m68476521.giphiertwo.api.MainRepository
import com.m68476521.giphiertwo.home.TrendingPaginationSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel
    @Inject
    constructor(
        mainRepository: MainRepository,
    ) : ViewModel() {
        val flow =
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 25),
            ) {
                TrendingPaginationSource(mainRepository)
            }.flow.cachedIn(viewModelScope)
    }
