package com.m68476521.giphierto.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.m68476521.giphierto.R
import com.m68476521.giphierto.models.SubcategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SubCategoryFragment : Fragment() {
    private val args: SubCategoryFragmentArgs by navArgs()
    private val subcategoryModel by viewModels<SubcategoryViewModel>()

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
        val imagesAdapter = CategoryAdapter(false)
        images.adapter = imagesAdapter
        subCategories(args.subcategory)

        subcategoryModel.getSubCategories().observe(
            viewLifecycleOwner,
            { subCategories ->
                imagesAdapter.swapCategories(subCategories.data)
            }
        )
    }

    private fun subCategories(category: String) {
        lifecycleScope.launch {
            subcategoryModel.querySubCategories(category)
        }
    }

    override fun onDestroyView() {
        images.adapter = null
        super.onDestroyView()
    }
}
