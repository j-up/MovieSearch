package com.jm.moviesearch.repository

import androidx.lifecycle.LiveData
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.data.remote.MovieResponse
import com.jm.moviesearch.source.LocalNaverDataSource
import com.jm.moviesearch.source.RemoteNaverDataSource
import retrofit2.Call

class NaverRepositoryImpl(
    private val localNaverDataSource: LocalNaverDataSource,
    private val remoteNaverDataSource: RemoteNaverDataSource
) : NaverRepository {
    override suspend fun getNaverMovie(keyWord: String): Call<MovieResponse> {
        return remoteNaverDataSource.getNaverMovie(keyWord)
    }

    override suspend fun insertBookmark(bookmark: NaverEntity.Bookmark): Long {
        return localNaverDataSource.insertBookmark(bookmark)
    }

    override suspend fun getAllBookmark(): List<NaverEntity.Bookmark> {
        return localNaverDataSource.getAllBookmark()
    }

    override fun getAllBookmarkLiveData(): LiveData<List<NaverEntity.Bookmark>> {
        return localNaverDataSource.getAllBookmarkLiveData()
    }

    override suspend fun deleteBookmark(title: String?, image: String?, link: String?): Int {
        return localNaverDataSource.deleteBookmark(title, image, link)
    }
}