package com.jm.moviesearch.api

import com.jm.moviesearch.data.remote.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {

    @GET("v1/search/movie.json")
    fun getNaverMovie(
        @Query("query") keyWord: String,
        @Query("display") display: Int = DEFAULT_DISPLAY
    ): Call<MovieResponse>

    companion object {
        private const val DEFAULT_DISPLAY = 100
    }
}

