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
import com.m68476521.giphierto.models.SubCategorySelectedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sub_category_selected.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubCategorySelectedFragment : Fragment() {
    private val args: SubCategorySelectedFragmentArgs by navArgs()
    private val subcategorySelectedModel: SubCategorySelectedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sub_category_selected, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subcategorySelectedModel.subCategorySelected = args.category
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        images.layoutManager = staggeredGridLayoutManager
        val imagesAdapter = SubcategoryAdapter()
        images.adapter = imagesAdapter

        postponeEnterTransition()

        lifecycleScope.launch {
            subcategorySelectedModel.subCategoryFlow.collectLatest { data ->
                startPostponedEnterTransitions()
                imagesAdapter.submitData(data)
            }
        }
    }

    private fun startPostponedEnterTransitions() {
        (requireView().parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onDestroyView() {
        images.adapter = null
        super.onDestroyView()
    }
}
