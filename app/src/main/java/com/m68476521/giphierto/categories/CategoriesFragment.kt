package com.m68476521.giphierto.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.m68476521.giphierto.databinding.FragmentCategoriesBinding
import com.m68476521.giphierto.models.CategoryViewModel
import com.m68476521.giphierto.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private var imagesAdapter = CategoryAdapter(true)
    private val categoryModel: CategoryViewModel by activityViewModels()

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.images.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.images.adapter = imagesAdapter
        categoryModel.categoriesData.observe(
            viewLifecycleOwner,
            {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data.let { response ->
                            response?.data?.let { data -> imagesAdapter.swapCategories(data) }
                        }
                    }
                    Status.LOADING -> { }
                    Status.ERROR -> { }
                }
            }
        )
    }

    override fun onDestroyView() {
        binding.images.adapter = null
        super.onDestroyView()
        _binding = null
    }
}
