package com.m68476521.giphiertwo.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.m68476521.giphiertwo.ImagesAdapter
import com.m68476521.giphiertwo.R
import com.m68476521.giphiertwo.databinding.FragmentTrendingBinding
import com.m68476521.giphiertwo.models.TrendingViewModel
import com.m68476521.giphiertwo.util.shortSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private val trendingModel: TrendingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        val imagesAdapter = ImagesAdapter()

        binding.images.layoutManager = staggeredGridLayoutManager
        binding.images.adapter = imagesAdapter

        postponeEnterTransition()

        lifecycleScope.launch {
            trendingModel.flow.collectLatest { pagingData ->
                startPostponedEnterTransitions()
                imagesAdapter.submitData(pagingData)
            }
        }

        lifecycleScope.launch {
            imagesAdapter.loadStateFlow.collectLatest { loadStates ->
                showProgressBar(loadStates.refresh is LoadState.Loading)
                showErrorMessage(loadStates.refresh is LoadState.Error)
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean) {
        val visible = if (isVisible) View.VISIBLE else View.GONE
        binding.progressBar.visibility = visible
    }

    private fun showErrorMessage(isVisible: Boolean) {
        if (isVisible) requireView().shortSnackBar(getString(R.string.errorMessage))
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuGoToSearch) {
            val next = TrendingFragmentDirections.actionTrendingToSearchFragment()
            requireView().findNavController().navigate(next)
            return false
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater,
    ) {
        inflater.inflate(R.menu.menu_trending, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchView = SearchView(requireContext())
        menu.findItem(R.id.menuGoToSearch).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnClickListener {
            val next = TrendingFragmentDirections.actionTrendingToSearchFragment()
            it.findNavController().navigate(next)
        }
    }
}
