package com.khaled.breadfasttask.data.repo.comment

import android.util.Log
import com.khaled.breadfasttask.data.model.Comment
import com.khaled.breadfasttask.data.remote.ApiService
import com.khaled.breadfasttask.helpers.network.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

val TAG = CommentsRepositoryImp::class.java.name

class CommentsRepositoryImp @Inject constructor(
    private val apiService: ApiService,
) : CommentsRepository {
    override suspend fun getPostComments(id: String): Resource<List<Comment>> {
        return try {
            val response = apiService.getPostComments(id)
            if (!response.isNullOrEmpty()) {
                Resource.Success(response)
            } else {
                Log.e(TAG, "getComments: empty")
                Resource.Error("")
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getComments: ${e.message()}")
            Resource.Error(e.message())
        } catch (e: IOException) {
            Log.e(TAG, "getComments: Net")
            Resource.NetworkError()
        } catch (e: Exception) {
            Log.e(TAG, "getComments: ${e.message}")
            Resource.ServerError()
        }
    }
}