package com.khaled.breadfasttask.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.breadfasttask.R
import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.databinding.FragmentHomeBinding
import com.khaled.breadfasttask.helpers.ext.showToast
import com.khaled.breadfasttask.helpers.network.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {


    private val viewModel: HomeViewModel by viewModels()
    var _binding: FragmentHomeBinding? = null
    val binding
        get() = _binding!!
    val adapter = PostAdapter(this::navigateToPostDetails)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initUi()
        observeFlow()
        fetchData()
    }

    private fun fetchData() {
        viewModel.getAllPosts()
    }

    private fun observeFlow() {
        lifecycleScope.launch {
            viewModel.allPostsFlow.collect {
                decideProgressVisibility(it is Resource.Loading)
                when (it) {
                    is Resource.Error -> {
                        showToast(it.msg ?: getString(R.string.server_error_msg))
                    }

                    is Resource.NetworkError -> {
                        showToast(R.string.io_error_msg)
                    }

                    is Resource.ServerError -> {
                        showToast(R.string.server_error_msg)
                    }

                    is Resource.Success -> onPostsFetched(it.data)
                    else -> {}
                }
            }
        }

    }

    private fun onPostsFetched(data: List<Post>) {
        adapter.submitList(data)
    }

    private fun decideProgressVisibility(isLoading: Boolean) {
        binding.apply {
            postRv.isVisible = !isLoading
            progressBar.isVisible = isLoading
        }
    }

    private fun initUi() {
        binding.apply {
            postRv.layoutManager = LinearLayoutManager(requireContext())
            postRv.adapter = adapter
        }
    }

    fun navigateToPostDetails(item: Post) {
        val bundle = bundleOf(
            "post" to item
        )
        findNavController().navigate(R.id.action_homeFragment_to_postDetailsFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}