package com.m68476521.giphierto.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
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
    private var word = ""
    private var searching = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        images.setHasFixedSize(true)
        images.layoutManager = staggeredGridLayoutManager

        images.adapter = imagesAdapter

        images.addOnScrollListener(object :
            PaginationScrollListener(images, staggeredGridLayoutManager) {
            override fun loadMoreItems() {
                if (!loading && !searching) loadMoreGiphs()
                else if (!isLoading) loadMoreGiphsByWord()
            }

            override val totalPageCount: Int = totalPages
            override val isLastPage: Boolean = lastPage
            override val isLoading: Boolean = loading
        })

        postponeEnterTransition()
        initialLoad()
    }

    private fun startPostponedEnterTransitions() {
        (requireView().parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
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
                this.startPostponedEnterTransitions()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_trending, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                word = query
                if (!loading)
                    searchGiphsByWord(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun searchGiphsByWord(word: String) {
        count = 0
        currentPage = PAGE_START
        totalPages = 0
        lastPage = false
        imagesAdapter.clear()

        searching = true
        loading = true

        val disposable = GiphyManager.giphyApi.search(word, count)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                loading = false
                count += it.pagination.count
                imagesAdapter.addAll(it.data)
                if (currentPage <= totalPages) imagesAdapter.addLoadingFooter() else lastPage = true
                this.startPostponedEnterTransitions()
            }, { it.printStackTrace() })
        compositeDisposable.add(disposable)
    }

    private fun loadMoreGiphsByWord() {
        loading = true
        currentPage += 1

        val disposable = GiphyManager.giphyApi.search(word, count)
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
}
