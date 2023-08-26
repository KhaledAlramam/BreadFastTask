package com.khaled.breadfasttask.ui.postdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khaled.breadfasttask.data.model.Comment
import com.khaled.breadfasttask.data.repo.comment.CommentsRepositoryImp
import com.khaled.breadfasttask.helpers.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: CommentsRepositoryImp
) : ViewModel() {

    private val _commentsFlow: MutableStateFlow<Resource<List<Comment>>> =
        MutableStateFlow(Resource.Idle)

    fun getPostComments(id: String) {
        _commentsFlow.value = Resource.Loading
        viewModelScope.launch {
            val response = repository.getPostComments(id)
            _commentsFlow.value = response
        }
    }

    val commentsFlow = _commentsFlow
}