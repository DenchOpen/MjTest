package com.dench.netlib

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetManager private constructor() {

    private var retrofit: Retrofit

    companion object {
        const val url = "https://api.github.com"
        val instance by lazy { NetManager() }
    }

    init {
        retrofit = Retrofit.Builder()
            .client(initClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
    }

    private fun initClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val headersInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request()
                val req = buildReqWithHeaders(request.newBuilder())
                return chain.proceed(req)
            }
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headersInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    // 添加Headers
    private fun buildReqWithHeaders(builder: Request.Builder): Request {
        return builder
            .addHeader("Content_Type", "application/json")
            .addHeader("charset", "UTF-8")
            .build()
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}