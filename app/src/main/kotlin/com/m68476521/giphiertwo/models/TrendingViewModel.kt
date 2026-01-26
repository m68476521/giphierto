package com.m68476521.giphiertwo.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.m68476521.giphiertwo.api.Image
//import com.m68476521.giphiertwo.api.MainRepository
//import com.morozco.data.MainRepository
//import com.m68476521.giphiertwo.home.TrendingPaginationSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel
    @Inject
    constructor(
//        mainRepository: MainRepository,
    ) : ViewModel() {
//        val flow =
//            Pager(
//                // Configure how data is loaded by passing additional properties to
//                // PagingConfig, such as prefetchDistance.
//                PagingConfig(pageSize = 25),
//            ) {
//                TrendingPaginationSource(mainRepository)
//            }.flow.cachedIn(viewModelScope)

//        val flow = Flow<PagingData<Image>> =

        private val _state = MutableStateFlow(TrendingViewState())
        val state: StateFlow<TrendingViewState> = _state

        fun handleIntent(intent: TrendingIntent) {
            viewModelScope.launch {
                when (intent) {
                    is TrendingIntent.SelectItem -> {
                        _state.update {
                            it.copy(
                                currentItemSelected = intent.item,
                            )
                        }
                    }

                    is TrendingIntent.ClearItemSelected -> {
                        _state.update {
                            it.copy(
                                currentItemSelected = null,
                            )
                        }
                    }
                }
            }
        }
    }

data class TrendingViewState(
    val loading: Boolean = false,
    val error: String? = null,
    val currentItemSelected: Image? = null,
)

sealed class TrendingIntent {
    data class SelectItem(
        val item: Image,
    ) : TrendingIntent()

    data object ClearItemSelected : TrendingIntent()
}
