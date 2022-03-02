package com.apps.nycschools.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiProvider {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://data.cityofnewyork.us")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder().apply {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    )
                }.build()
            )
            .build()
    }

    fun <T> `for`(cls: Class<T>): T {
        return retrofit.create(cls)
    }
}