package com.morozco.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morozco.domain.giftevents.GiftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
//    mainRepository: MainRepository,
    private val orderEventsUseCase: GiftUseCase,
) : ViewModel() {
    val flow =
        Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 25),
        ) {
            TrendingPaginationSource(mainRepository)
        }.flow.cachedIn(viewModelScope)

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
