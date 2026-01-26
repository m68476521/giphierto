package com.m68476521.giphiertwo.categories

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
import com.m68476521.giphiertwo.databinding.FragmentSubCategorySelectedBinding
import com.m68476521.giphiertwo.models.SubCategorySelectedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubCategorySelectedFragment : Fragment() {
    private lateinit var binding: FragmentSubCategorySelectedBinding

    private val args: SubCategorySelectedFragmentArgs by navArgs()
    private val subcategorySelectedModel: SubCategorySelectedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSubCategorySelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        subcategorySelectedModel.subCategorySelected = args.category
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.images.layoutManager = staggeredGridLayoutManager
        val imagesAdapter = SubcategoryAdapter()
        binding.images.adapter = imagesAdapter

        postponeEnterTransition()

        lifecycleScope.launch {
//            subcategorySelectedModel.subCategoryFlow.collectLatest { data ->
//                startPostponedEnterTransitions()
//                imagesAdapter.submitData(data)
//            }
        }
    }

    private fun startPostponedEnterTransitions() {
        (requireView().parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onDestroyView() {
        binding.images.adapter = null
        super.onDestroyView()
    }
}
