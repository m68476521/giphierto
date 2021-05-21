package com.m68476521.giphierto.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.m68476521.giphierto.R
import com.m68476521.giphierto.databinding.FragmentSearchBinding
import com.m68476521.giphierto.models.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchModel: SearchViewModel by viewModels()
    private lateinit var imagesAdapter: ImagesSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.images.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        imagesAdapter = ImagesSearchAdapter()
        binding.images.adapter = imagesAdapter

        if (searchModel.isInitialized()) {
            postponeEnterTransition()
            lifecycleScope.launch {
                searchModel.searchFlow2.collectLatest { pagingData ->
                    startPostponedEnterTransitions()
                    imagesAdapter.submitData(pagingData)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.menuItemSearch).apply {
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                lifecycleScope.launch {
                    searchModel.searchByWord(query)
                        .collectLatest { pagingData ->
                            startPostponedEnterTransitions()
                            imagesAdapter.submitData(pagingData)
                        }
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
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
