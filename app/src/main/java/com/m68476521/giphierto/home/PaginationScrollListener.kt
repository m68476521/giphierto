package com.m68476521.giphierto.home

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class PaginationScrollListener(
    var layoutManager: RecyclerView,
    var staggeredGridLayoutManager: StaggeredGridLayoutManager
) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = staggeredGridLayoutManager.itemCount

        val lastVisibleItemPositions =
            (staggeredGridLayoutManager).findLastVisibleItemPositions(null)
        val lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)

        if (!isLoading && !isLastPage && visibleItemCount + lastVisibleItemPosition >=
            totalItemCount && lastVisibleItemPosition >= 0
        ) {
            loadMoreItems()
        }
    }

    open fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0

        lastVisibleItemPositions.indices.forEach { i ->
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    abstract fun loadMoreItems()
    abstract val totalPageCount: Int
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean
}
