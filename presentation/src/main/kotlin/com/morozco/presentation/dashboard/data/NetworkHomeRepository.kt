//package com.morozco.presentation.dashboard.data
//
//import android.net.http.HttpException
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.m68476521.networking.MainAPI
//
//import com.m68476521.networking.MainAPIInterface
//import com.morozco.core.model.Image
//import com.morozco.presentation.dashboard.domain.HomeRepository
//
//class NetworkHomeRepository(
//    private val api: MainAPIInterface,
//): HomeRepository {
//    override fun pagingSourceForTrending(
//        type: String,
//        pagination: Int,
//        limit: Int
//    ): PagingSource<Int, Image> {
//        return HomePagingSource(
//            type = type,
//            pagination = pagination,
//            limit = limit,
//            api = api
//        )
//
//    }
//}
//
//class HomePagingSource(
//    private val type: String,
//    private val pagination: Int,
//    private val limit: Int,
//    private val api: MainAPIInterface,
//) : PagingSource<Int, Image>() {
//
//    override fun getRefreshKey(state: PagingState<Int, Image>): Int? =
//        state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
//        return try {
//            val nextPageNumber = (params.key ?: 0) + 1
////            val page = if (params.key == null) 0 else (params.key!! * 25)
//            val result = api.getTrending(type = type, pagination = pagination, limit = limit)
////            val nextNumber = nextPageNumber.plus(1)
////            when {
////                response.isSuccessful -> {
////                    val res = Resource.success(response.body()).data
////                    if (res != null) {
////                        LoadResult.Page(
////                            data = res.data,
////                            prevKey = null, // Only paging forward.
////                            nextKey = nextNumber,
////                        )
////                    } else {
////                        returnEmpty(nextNumber)
////                    }
////                }
////                else -> return returnEmpty(nextNumber)
////            }
//            result.getOrNull()?.let { response ->
//                return LoadResult.Page(
//                    data = response.data,
//                    prevKey = null,
//                    nextKey = nextPageNumber
//                )
//            }
//            return LoadResult.Error(Exception("Something went wrong A"))//TODO check this
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
////        catch (e: HttpException) {
////            LoadResult.Error(e)
////        }
//    }
//
//    private fun returnEmpty(nextPageNumber: Int): LoadResult.Page<Int, Image> =
//        LoadResult.Page(
//            data = emptyList(),
//            prevKey = null,
//            nextKey = nextPageNumber,
//        )
//}