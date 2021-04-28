package com.m68476521.giphierto.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.m68476521.giphierto.R
import com.m68476521.giphierto.models.SubcategoryViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_sub_category_selected.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SubCategorySelectedFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private val args: SubCategorySelectedFragmentArgs by navArgs()
    private val subcategoryModel by viewModels<SubcategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sub_category_selected, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subcategoryModel.category = args.category
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        images.layoutManager = staggeredGridLayoutManager
        val imagesAdapter = SubcategoryAdapter()
        images.adapter = imagesAdapter

        postponeEnterTransition()

        lifecycleScope.launch {
            subcategoryModel.subcategoryflow.collectLatest { pagindData ->
                startPostponedEnterTransitions()
                imagesAdapter.submitData(pagindData)
            }
        }
    }

    private fun startPostponedEnterTransitions() {
        (requireView().parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
