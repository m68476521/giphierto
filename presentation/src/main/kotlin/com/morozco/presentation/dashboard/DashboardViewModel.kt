package com.morozco.presentation.dashboard

import android.media.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.morozco.core.model.Rating
import com.morozco.domain.giftevents.GiftEventsResult
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
    private val useCase: GiftUseCase,
) : ViewModel(), DashboardPresentation {// TODO FIX PAGINATION
//    val flow =
//        Pager(
//            // Configure how data is loaded by passing additional properties to
//            // PagingConfig, such as prefetchDistance.
//            PagingConfig(pageSize = 25),
//        ) {
//            TrendingPaginationSource(mainRepository)
//        }.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            println("MKE A")
            val response = useCase.getGiftEvents(
                type = Rating.PG_13.rating,
                pagination = 0,
                limit = 25,
            )

            println("MKE on response $response")

//            when (response) {
//                is GiftEventsResult.EventsFetched -> {
//
//                }
//
//                is GiftEventsResult.EmptyData -> {
//
//                }
//
//                is GiftEventsResult.Failure -> {
//
//                } else -> {
//
//                }
//            }
        }
    }

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
