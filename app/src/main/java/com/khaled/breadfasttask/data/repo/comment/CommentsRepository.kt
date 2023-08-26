package com.khaled.breadfasttask.data.repo.comment

import com.khaled.breadfasttask.data.model.Comment
import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.helpers.network.Resource

interface CommentsRepository {

    suspend fun getPostComments(id: String): Resource<List<Comment>>
}