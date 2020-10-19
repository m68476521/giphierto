package com.m68476521.giphierto.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.m68476521.giphierto.R
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.models.CategoryViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*

class CategoriesFragment : Fragment() {
    private var imagesAdapter = CategoryAdapter(true)
    private val compositeDisposable = CompositeDisposable()
    private val categoryModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        images.layoutManager = GridLayoutManager(requireContext(), 3)
        images.adapter = imagesAdapter
        if (categoryModel.categories.isEmpty())
            categories()
        else
            imagesAdapter.swapCategories(categoryModel.categories)
    }

    private fun categories() {
        val disposable = GiphyManager.giphyApi.categories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    categoryModel.categories = it.data
                    imagesAdapter.swapCategories(it.data)
                },
                { it.printStackTrace() }
            )
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
