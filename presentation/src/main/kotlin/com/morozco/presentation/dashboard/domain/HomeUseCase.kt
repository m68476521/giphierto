package com.morozco.presentation.dashboard.domain

import androidx.paging.PagingSource
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.RelatedData
import com.morozco.core.model.Data
import com.morozco.core.model.Image
import com.morozco.domain.giftevents.HomeRepository

class HomeUseCase(
    private val repository: HomeRepository
) {
    suspend fun getGiftEvents(
        type: String,
        pagination: Int,
        limit: Int) : GetGiftEventsResult {
        val result = repository.getTrending(type, pagination, limit)

        result.getOrNull()?.let {
            return GetGiftEventsResult.FetchingSuccess(it)
        } ?: run {
            return GetGiftEventsResult.EmptyData
        }
        return GetGiftEventsResult.Failure
    }

    fun pagingSourceForTrending(
        type: String,
        pagination: Int,
        limit: Int
    ) : PagingSource<Int, Image> {
        println("MKE900015")
        println("MKE900015 MKE pagingSourceForTrending-> $type")
        return repository.pagingSourceForTrending(type, pagination, limit)
    }

    suspend fun getCategories(): GetCategoriesResult {
        val result = repository.getCategories()
        println("MKE result :::: $result")
        result.getOrNull()?.let {
            return GetCategoriesResult.FetchingSuccess(it)
        }
        return GetCategoriesResult.Failure

    }

    fun pagingSourceForCategories(): PagingSource<Int, Data> {
        println("MKE pagingSourceForCategories->")
        return repository.pagingSourceForCategories()
    }

    suspend fun getRelatedGifts(giftId: String, limit: Int = 10): GetRelatedResult {
        val result = repository.getRelated(giftId, limit)

        result.getOrNull()?.let {
            return GetRelatedResult.FetchingSuccess(it)
        }

        return GetRelatedResult.FetchingFailed
    }


    sealed class GetGiftEventsResult {
        data class FetchingSuccess(
            val events: ImageResponse
        ) : GetGiftEventsResult()

        data object EmptyData : GetGiftEventsResult()

        data object Failure : GetGiftEventsResult()

    }

    sealed class GetCategoriesResult {
        data class FetchingSuccess(
            val events: CategoryData
        ) : GetCategoriesResult()

        data object EmptyData : GetCategoriesResult()

        data object Failure : GetCategoriesResult()
    }

    sealed class GetRelatedResult {
        data class FetchingSuccess(
            val related: RelatedData
        ): GetRelatedResult()

        data object FetchingFailed: GetRelatedResult()
    }

}