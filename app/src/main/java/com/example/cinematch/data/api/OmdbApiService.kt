package com.example.cinematch.data.api

import com.example.cinematch.BuildConfig
import com.example.cinematch.data.model.MovieDetail
import com.example.cinematch.data.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {

    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("type") type: String? = null,
        @Query("page") page: Int = 1,
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): MovieSearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full",
        @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY
    ): MovieDetail
}
