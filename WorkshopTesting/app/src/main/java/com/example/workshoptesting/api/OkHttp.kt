package com.example.workshoptesting.api

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient

interface OkHttp {

    companion object {
        val instance: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
        }
    }
}
