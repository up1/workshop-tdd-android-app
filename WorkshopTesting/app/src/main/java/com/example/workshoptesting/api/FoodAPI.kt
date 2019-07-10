package com.example.workshoptesting.api

import com.example.workshoptesting.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodAPI {

    @GET("search?key=" + BuildConfig.FOOD2FORK_API_KEY)
    fun search(@Query("q") query: String): Call<FoodsContainer>

    companion object Factory {
        fun create(): FoodAPI {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://food2fork.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(FoodAPI::class.java)
        }
    }

}