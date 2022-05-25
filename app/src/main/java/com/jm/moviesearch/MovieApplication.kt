package com.jm.moviesearch

import android.app.Application
import com.jm.moviesearch.di.appModule
import com.jm.moviesearch.di.databaseModule
import com.jm.moviesearch.di.networkModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MovieApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MovieApplication)
            modules(appModule, networkModules, databaseModule)
        }
    }
}