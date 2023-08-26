package com.khaled.breadfasttask.helpers.network

import com.khaled.breadfasttask.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * Interceptor which adds headers from shared preferences according to the added custom headers,
 * Authentication, languageCode and level headers by default.
 */
class MyServiceInterceptor
 internal constructor() : Interceptor {

    private var requestBuilder: Request.Builder? = null

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        requestBuilder = request.newBuilder()
        requestBuilder!!.addHeader("Content-Type", "application/json")
        addAuthenticationHeader()
        return chain.proceed(requestBuilder!!.build())
    }


    private fun addAuthenticationHeader() {
        requestBuilder!!.addHeader("authorization", BuildConfig.API_TOKEN)
    }
}