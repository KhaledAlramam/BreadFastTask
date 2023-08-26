package com.khaled.breadfasttask.ui.postdetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaled.breadfasttask.R
import com.khaled.breadfasttask.data.model.Comment
import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.databinding.FragmentPostDetailsBinding
import com.khaled.breadfasttask.helpers.ext.showToast
import com.khaled.breadfasttask.helpers.network.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {

    private val viewModel: CommentsViewModel by viewModels()
    var _binding: FragmentPostDetailsBinding? = null
    val binding
        get() = _binding!!
    val adapter = CommentsAdapter()
    var post: Post? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostDetailsBinding.bind(view)
        initUi()
        observeFlow()
        fetchData()
    }

    private fun initUi() {
        post = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("post", Post::class.java)
        }else{
            arguments?.getParcelable("post") as Post?
        }
        binding.apply {
            commentsRv.layoutManager = LinearLayoutManager(requireContext())
            commentsRv.adapter = adapter
            includePostHeader.postTitle.text = post?.title
            includePostHeader.postContent.text = post?.body
        }

    }

    private fun fetchData() {
        post?.let {
            viewModel.getPostComments(it.id)
        }
    }
    private fun observeFlow() {
        lifecycleScope.launch {
            viewModel.commentsFlow.collect {
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

                    is Resource.Success -> onCommentsFetched(it.data)
                    else -> {}
                }
            }
        }

    }

    private fun onCommentsFetched(data: List<Comment>) {
        adapter.submitList(data)
    }

    private fun decideProgressVisibility(isLoading: Boolean) {
        binding.apply {
            commentsRv.isVisible = !isLoading
            progressBar.isVisible = isLoading
        }
    }

}