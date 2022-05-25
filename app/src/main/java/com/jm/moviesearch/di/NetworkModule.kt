package com.jm.moviesearch.di

import com.jm.moviesearch.api.NaverApi
import com.jm.moviesearch.repository.NaverRepository
import com.jm.moviesearch.repository.NaverRepositoryImpl
import com.jm.moviesearch.source.LocalNaverDataSource
import com.jm.moviesearch.source.RemoteNaverDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val BASE_URL = "https://openapi.naver.com/"

private const val NAVER_CLIENT_ID_KEY = "X-Naver-Client-Id"
private const val NAVER_CLIENT_ID_VALUE = "mM4YEkqkdnkFugQIUSQC"

private const val NAVER_CLIENT_SECRET_KEY = "X-Naver-Client-Secret"
private const val NAVER_CLIENT_SECRET_VALUE = "cX8bx1ykNF"

val networkModules = module {
    single { createNaverApi() }
    single { RemoteNaverDataSource(get()) }
    single { LocalNaverDataSource(get()) }
    single { NaverRepositoryImpl(get(), get()) as NaverRepository }
}

fun createNaverApi(): NaverApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NaverApi::class.java)
}

private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
        = OkHttpClient.Builder().run {
    addInterceptor(interceptor)
    build()
}

class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader(NAVER_CLIENT_ID_KEY, NAVER_CLIENT_ID_VALUE)
            .addHeader(NAVER_CLIENT_SECRET_KEY, NAVER_CLIENT_SECRET_VALUE)
            .build()
        proceed(newRequest)
    }
}
