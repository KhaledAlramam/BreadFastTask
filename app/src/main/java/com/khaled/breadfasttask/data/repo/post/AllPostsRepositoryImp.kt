package com.khaled.breadfasttask.data.repo.post

import android.util.Log
import com.khaled.breadfasttask.data.model.Post
import com.khaled.breadfasttask.data.remote.ApiService
import com.khaled.breadfasttask.helpers.network.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
val TAG = AllPostsRepositoryImp::class.java.name
class AllPostsRepositoryImp @Inject constructor(
    private val apiService: ApiService,
): AllPostsRepository {
    override suspend fun getAllPosts(): Resource<List<Post>> {
        return try {
            val response = apiService.getAllPosts()
            if (!response.isNullOrEmpty()) {
                Resource.Success(response)
            } else {
                Log.e(TAG, "getAllPosts: empty", )
                Resource.Error("")
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getAllPosts: ${e.message()}", )
            Resource.Error(e.message())
        } catch (e: IOException) {
            Log.e(TAG, "getAllPosts: Net", )
            Resource.NetworkError()
        } catch (e: Exception) {
            Log.e(TAG, "getAllPosts: ${e.message}", )
            Resource.ServerError()
        }
    }
}