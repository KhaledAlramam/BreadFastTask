package com.khaled.breadfasttask.helpers.network

sealed class Resource<out T> {

    object Loading : Resource<Nothing>()
    object Idle : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val msg: String?, val responseCode: String? = null) : Resource<T>()
    class NetworkError<out T> : Resource<T>()
    class ServerError<out T> : Resource<T>()
}