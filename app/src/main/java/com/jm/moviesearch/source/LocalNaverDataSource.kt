package com.jm.moviesearch.source

import androidx.lifecycle.LiveData
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.data.local.dao.BookmarkDao
import com.jm.moviesearch.data.remote.MovieResponse
import retrofit2.Call

class LocalNaverDataSource(private val bookmarkDao: BookmarkDao): NaverDataSource {
    override suspend fun getNaverMovie(keyWord: String): Call<MovieResponse> {
        throw UnsupportedOperationException()
    }

    override suspend fun insertBookmark(bookmark: NaverEntity.Bookmark): Long {
        return bookmarkDao.insertBookmark(bookmark)
    }

    override suspend fun getAllBookmark(): List<NaverEntity.Bookmark> {
        return bookmarkDao.getAllBookmark()
    }

    override fun getAllBookmarkLiveData(): LiveData<List<NaverEntity.Bookmark>> {
        return bookmarkDao.getAllBookmarkLiveData()
    }

    override suspend fun deleteBookmark(title: String?, image: String?, link: String?): Int {
        return bookmarkDao.deleteBookmark(title, image, link)
    }
}