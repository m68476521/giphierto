package com.m68476521.giphierto.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.m68476521.giphierto.api.GiphyManager
import com.m68476521.giphierto.databinding.FragmentCategoriesBinding
import com.m68476521.giphierto.models.CategoryViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoriesFragment : Fragment() {
    private var imagesAdapter = CategoryAdapter(true)
    private val compositeDisposable = CompositeDisposable()
    private val categoryModel: CategoryViewModel by activityViewModels()
    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.images.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.images.adapter = imagesAdapter
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
