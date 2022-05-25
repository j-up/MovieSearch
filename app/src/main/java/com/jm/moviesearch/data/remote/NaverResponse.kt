package com.jm.moviesearch.data.remote

import com.google.gson.annotations.SerializedName
import com.jm.moviesearch.data.NaverEntity

data class MovieResponse(
    @SerializedName("display") val display: Int,
    @SerializedName("items") val movieList: List<NaverEntity.Movie>,
    @SerializedName("lastBuildDate") val lastBuildDate: String,
    @SerializedName("start") val start: Int,
    @SerializedName("total") val total: Int
)
