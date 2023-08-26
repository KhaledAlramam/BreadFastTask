package com.khaled.breadfasttask.data.remote

import com.khaled.breadfasttask.data.model.Comment
import com.khaled.breadfasttask.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/public/v2/posts")
    suspend fun getAllPosts(): List<Post>

    @GET("/public/v2/posts/{id}/comments")
    suspend fun getPostComments(
        @Path("id") id: String
    ): List<Comment>


}