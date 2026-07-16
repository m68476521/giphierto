package com.morozco.data

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.m68476521.networking.MainAPIInterface
import com.m68476521.networking.request.CategoryData
import com.m68476521.networking.request.ImageResponse
import com.m68476521.networking.request.RelatedData
import com.m68476521.networking.request.toResult
import com.morozco.core.model.Data
import com.morozco.core.model.Image
import com.morozco.domain.giftevents.HomeRepository
import java.io.IOException

class NetworkHomeRepository(
    private val api: MainAPIInterface,
) : HomeRepository {
    override suspend fun getTrending(
        type: String,
        pagination: Int,
        limit: Int,
    ): Result<ImageResponse> =
        api
            .getTrending(
                rating = "",
                type = type,
                offset = pagination,
                limit = limit,
            ).toResult()

    override fun pagingSourceForTrending(
        type: String,
        pagination: Int,
        limit: Int,
    ): PagingSource<Int, Image> =
        HomePagingSource(
            type = type,
            pagination = pagination,
            limit = limit,
            api = api,
        )

    override suspend fun getCategories(): Result<CategoryData> = api.getCategories().toResult()

    override fun pagingSourceForCategories(): PagingSource<Int, Data> = CategoriesPagingSource(limit = 25, api = api)

    override suspend fun getRelated(
        giftId: String,
        limit: Int,
    ): Result<RelatedData> = api.getRelated(giftId, limit).toResult()
}

class HomePagingSource(
    private val type: String,
    private val pagination: Int,
    private val limit: Int,
    private val api: MainAPIInterface,
) : PagingSource<Int, Image>() {
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? = state.anchorPosition

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> =
        try {
            val position = params.key ?: pagination
            val result =
                api.getTrending(
                    rating = "",
                    type = type,
                    offset = position,
                    limit = params.loadSize,
                )

            val response = result.getOrNull()

            if (response != null) {
                val nextOffset = position + response.pagination.count
                val totalCount = response.pagination.totalCount
                LoadResult.Page(
                    data = response.data,
                    prevKey =
                        if (position == pagination) {
                            null
                        } else {
                            position - response.pagination.count
                        },
                    nextKey =
                        if (response.data.isEmpty() ||
                            (totalCount != null && nextOffset >= totalCount)
                        ) {
                            null
                        } else {
                            nextOffset
                        },
                )
            } else {
                LoadResult.Error(Exception("Something went wrong loading trending images"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
}

class CategoriesPagingSource(
    private val limit: Int,
    private val api: MainAPIInterface,
) : PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? = null

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> =
        try {
            val result = api.getCategories()
            val response = result.getOrNull()

            if (response != null) {
                LoadResult.Page(
                    data = response.data,
                    prevKey = null,
                    nextKey = null,
                )
            } else {
                LoadResult.Error(Exception("Something went wrong loading categories"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
}
