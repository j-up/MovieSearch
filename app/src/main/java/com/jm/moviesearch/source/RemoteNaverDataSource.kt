package com.jm.moviesearch.source

import androidx.lifecycle.LiveData
import com.jm.moviesearch.api.NaverApi
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.data.remote.MovieResponse
import retrofit2.Call

class RemoteNaverDataSource(private val naverApi: NaverApi): NaverDataSource {
    override suspend fun getNaverMovie(keyWord: String): Call<MovieResponse> {
        return naverApi.getNaverMovie(keyWord)
    }

    override suspend fun insertBookmark(bookmark: NaverEntity.Bookmark): Long {
        throw UnsupportedOperationException()
    }

    override suspend fun getAllBookmark(): List<NaverEntity.Bookmark> {
        throw UnsupportedOperationException()
    }

    override fun getAllBookmarkLiveData(): LiveData<List<NaverEntity.Bookmark>> {
        throw UnsupportedOperationException()
    }

    override suspend fun deleteBookmark(title: String?, image: String?, link: String?): Int {
        throw UnsupportedOperationException()
    }
}