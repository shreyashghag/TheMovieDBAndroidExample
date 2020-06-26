package com.android.onerooftechnologiestask.network

import com.android.onerooftechnologiestask.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GetMovieDataService {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") userkey: String?
    ): Call<Movie>
}