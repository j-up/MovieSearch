package com.jm.moviesearch.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jm.moviesearch.data.NaverEntity

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: NaverEntity.Bookmark): Long

    @Query("SELECT * FROM Bookmark")
    suspend fun getAllBookmark(): List<NaverEntity.Bookmark>

    @Query("SELECT * FROM Bookmark")
    fun getAllBookmarkLiveData(): LiveData<List<NaverEntity.Bookmark>>

    @Query("DELETE FROM Bookmark WHERE title = (:title) And image = (:image) AND link = (:link)")
    suspend fun deleteBookmark(title: String?, image: String?, link: String?): Int
}