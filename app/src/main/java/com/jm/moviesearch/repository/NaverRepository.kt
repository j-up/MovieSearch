package com.jm.moviesearch.repository

import androidx.lifecycle.LiveData
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.data.remote.MovieResponse
import retrofit2.Call

interface NaverRepository {
    /*
    RemoteNaverDataSource
     */
    suspend fun getNaverMovie(keyWord: String): Call<MovieResponse>

    /*
    LocalNaverDataSource
    */
    suspend fun insertBookmark(bookmark: NaverEntity.Bookmark): Long

    suspend fun getAllBookmark(): List<NaverEntity.Bookmark>

    fun getAllBookmarkLiveData(): LiveData<List<NaverEntity.Bookmark>>

    suspend fun deleteBookmark(title: String?, image: String?, link: String?): Int
}