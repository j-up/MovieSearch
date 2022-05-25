package com.jm.moviesearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.data.local.dao.BookmarkDao

@Database(entities = [NaverEntity.Bookmark::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}