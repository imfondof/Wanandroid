package com.imfondof.wanandroid.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceCreator {
    private const val BASE_URL = "https://www.wanandroid.com/"

    private val httpClient = OkHttpClient.Builder().addInterceptor(HttpLogInterceptor())

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            httpClient.addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (com.imfondof.wanandroid.BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                )
            ).build()
        )
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())


    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}