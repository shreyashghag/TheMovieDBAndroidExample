package com.android.onerooftechnologiestask.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance{

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://api.themoviedb.org/3/"

    var gson = GsonBuilder()
        .setLenient()
        .create()
    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }
}