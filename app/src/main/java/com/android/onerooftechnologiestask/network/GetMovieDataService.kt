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

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") userkey: String?
    ): Call<Movie>

    @GET("search/movie")
    fun getSearchedMovies(
        @Query("query") query: String,
        @Query("api_key") userkey: String?,
        @Query("page") page: Int
    ): Call<Movie>
}