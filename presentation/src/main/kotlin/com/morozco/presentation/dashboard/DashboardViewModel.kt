package com.morozco.presentation.dashboard

import android.media.Image
import androidx.lifecycle.ViewModel
import com.morozco.domain.giftevents.GiftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    private val orderEventsUseCase: GiftUseCase,
) : ViewModel(), DashboardPresentation {// TODO FIX PAGINATION
//    val flow =
//        Pager(
//            // Configure how data is loaded by passing additional properties to
//            // PagingConfig, such as prefetchDistance.
//            PagingConfig(pageSize = 25),
//        ) {
//            TrendingPaginationSource(mainRepository)
//        }.flow.cachedIn(viewModelScope)

    private val _state = MutableStateFlow(DashboardUIState())
    override val state: StateFlow<DashboardUIState> = _state

    fun handleIntent(intent: TrendingIntent) {
//        viewModelScope.launch {
//            when (intent) {
//                is TrendingIntent.SelectItem -> {
//                    _state.update {
//                        it.copy(
//                            currentItemSelected = intent.item,
//                        )
//                    }
//                }
//
//                is TrendingIntent.ClearItemSelected -> {
//                    _state.update {
//                        it.copy(
//                            currentItemSelected = null,
//                        )
//                    }
//                }
//            }
//        }
    }

    override fun navigateToNext() {
        TODO("Not yet implemented")
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
