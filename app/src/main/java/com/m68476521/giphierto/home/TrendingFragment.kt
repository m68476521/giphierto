package com.m68476521.giphierto.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.m68476521.giphierto.ImagesAdapter
import com.m68476521.giphierto.R
import com.m68476521.giphierto.api.GiphyManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_trending.*

private const val PAGE_START = 0

class TrendingFragment : Fragment() {
    private var imagesAdapter = ImagesAdapter()
    private val compositeDisposable = CompositeDisposable()

    private var loading = false
    private var lastPage = false
    private var totalPages = 0
    private var currentPage = PAGE_START
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        images.setHasFixedSize(true)
        images.layoutManager = staggeredGridLayoutManager

        images.adapter = imagesAdapter

        images.addOnScrollListener(object : PaginationScrollListener(images, staggeredGridLayoutManager) {
            override fun loadMoreItems() {
                if (!loading) loadMoreGiphs()
            }

            override val totalPageCount: Int = totalPages
            override val isLastPage: Boolean = lastPage
            override val isLoading: Boolean = loading
        })
        initialLoad()
    }

    private fun initialLoad() {
        val disposable = GiphyManager.giphyApi.trending()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                imagesAdapter.addAll(it.data)
                count += it.pagination.count
                loading = false
                if (currentPage <= totalPages) imagesAdapter.addLoadingFooter() else lastPage = true
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }

    private fun loadMoreGiphs() {
        loading = true
        currentPage += 1

        val disposable = GiphyManager.giphyApi.trending(pagination = count)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                imagesAdapter.removeLoadingFooter()
                loading = false
                count += it.pagination.count
                imagesAdapter.addAll(it.data)
                if (currentPage != totalPages) imagesAdapter.addLoadingFooter() else lastPage = true
            }, { it.printStackTrace() })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
