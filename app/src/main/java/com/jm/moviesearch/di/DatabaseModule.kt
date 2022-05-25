package com.jm.moviesearch.di

import androidx.room.Room
import com.jm.moviesearch.data.local.MovieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, DATABASE_NAME
        ).build()
    }

    single { get<MovieDatabase>().bookmarkDao() }
}

const val DATABASE_NAME:String = "bookmark.db"