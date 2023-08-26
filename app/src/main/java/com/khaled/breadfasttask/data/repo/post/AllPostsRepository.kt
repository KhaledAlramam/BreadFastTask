package com.khaled.breadfasttask.data.repo.post

import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.helpers.network.Resource

interface AllPostsRepository {

    suspend fun getAllPosts(): Resource<List<Post>>
}