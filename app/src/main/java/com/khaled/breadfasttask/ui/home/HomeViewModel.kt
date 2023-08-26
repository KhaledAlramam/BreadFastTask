package com.khaled.breadfasttask.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.data.repo.post.AllPostsRepositoryImp
import com.khaled.breadfasttask.helpers.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AllPostsRepositoryImp
) : ViewModel() {

    private val _allPostsFlow: MutableStateFlow<Resource<List<Post>>> =
        MutableStateFlow(Resource.Idle)

    fun getAllPosts() {
        _allPostsFlow.value = Resource.Loading
        viewModelScope.launch {
            val response = repository.getAllPosts()
            _allPostsFlow.value = response
        }
    }

    val allPostsFlow = _allPostsFlow
}