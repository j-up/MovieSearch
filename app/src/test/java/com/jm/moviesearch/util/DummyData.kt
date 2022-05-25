package com.jm.moviesearch.util

import com.jm.moviesearch.data.NaverEntity
import com.jm.moviesearch.data.remote.MovieResponse

object DummyData {

    val bookmarkedMovieList:List<NaverEntity.Movie> = listOf(
        NaverEntity.Movie(
            title = "굿바이, 나의 <b>소련</b>",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=204441",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/2044/204441_P01_141237.JPG",
            subtitle = "GOODBYE, SOVIET UNION, pubDate=2020, director=로리 랜들러|, actor=, userRating=0.00",
            director = "",
            actor = "",
            userRating = "",
            pubDate = "",
            isBookmark = true
        ),
        NaverEntity.Movie(
            title = "<b>소련</b> KGB, link=https://movie.naver.com/movie/bi/mi/basic.nhn?code=14855",
            link = "",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0148/14855_P01_123203.jpg",
            subtitle = "The Fourth Protocol, pubDate=1987, director=존 맥켄지|, actor=마이클 케인|피어스 브로스넌|",
            director = "",
            actor = "",
            userRating = "7.60",
            pubDate = "",
            isBookmark = false
        ), NaverEntity.Movie(
            title = "파랑새",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=32230",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0322/32230_P01_142333.jpg",
            subtitle = "The Blue Bird, pubDate=1976, director=조지 큐커|, actor=제인 폰다|에바 가드너|",
            director = "",
            actor = "",
            userRating = "0.00",
            pubDate = "",
            isBookmark = false
        ), NaverEntity.Movie(
            title = "007 위기일발",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=10156",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0101/A0156-01.jpg",
            subtitle = "From Russia With Love, pubDate=1963",
            director = "테렌스 영|",
            actor = "숀 코네리|",
            userRating = "7.88",
            pubDate = "",
            isBookmark = false
        )
    )

    val bookmarkList:List<NaverEntity.Bookmark> = listOf(
        NaverEntity.Bookmark(
            title = "굿바이, 나의 <b>소련</b>",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=204441",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/2044/204441_P01_141237.JPG",
            subtitle = "GOODBYE, SOVIET UNION, pubDate=2020, director=로리 랜들러|, actor=, userRating=0.00",
            director = "",
            actor = "",
            userRating = "",
            pubDate = ""
        )
    )

    val movieList:List<NaverEntity.Movie> = listOf(
        NaverEntity.Movie(
            title = "굿바이, 나의 <b>소련</b>",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=204441",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/2044/204441_P01_141237.JPG",
            subtitle = "GOODBYE, SOVIET UNION, pubDate=2020, director=로리 랜들러|, actor=, userRating=0.00",
            director = "",
            actor = "",
            userRating = "",
            pubDate = "",
            isBookmark = false
        ),
        NaverEntity.Movie(
            title = "<b>소련</b> KGB, link=https://movie.naver.com/movie/bi/mi/basic.nhn?code=14855",
            link = "",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0148/14855_P01_123203.jpg",
            subtitle = "The Fourth Protocol, pubDate=1987, director=존 맥켄지|, actor=마이클 케인|피어스 브로스넌|",
            director = "",
            actor = "",
            userRating = "7.60",
            pubDate = "",
            isBookmark = false
        ), NaverEntity.Movie(
            title = "파랑새",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=32230",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0322/32230_P01_142333.jpg",
            subtitle = "The Blue Bird, pubDate=1976, director=조지 큐커|, actor=제인 폰다|에바 가드너|",
            director = "",
            actor = "",
            userRating = "0.00",
            pubDate = "",
            isBookmark = false
        ), NaverEntity.Movie(
            title = "007 위기일발",
            link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=10156",
            image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/0101/A0156-01.jpg",
            subtitle = "From Russia With Love, pubDate=1963",
            director = "테렌스 영|",
            actor = "숀 코네리|",
            userRating = "7.88",
            pubDate = "",
            isBookmark = false
        )
    )

    val bookmarkItem: NaverEntity.Bookmark = NaverEntity.Bookmark(
        title = "굿바이, 나의 <b>소련</b>",
        link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=204441",
        image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/2044/204441_P01_141237.JPG",
        subtitle = "GOODBYE, SOVIET UNION, pubDate=2020, director=로리 랜들러|, actor=, userRating=0.00",
        director = "",
        actor = "",
        userRating = "",
        pubDate = ""
    )

    val movieItemBookmarkIsTrue: NaverEntity.Movie = NaverEntity.Movie(
        title = "굿바이, 나의 <b>소련</b>",
        link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=204441",
        image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/2044/204441_P01_141237.JPG",
        subtitle = "GOODBYE, SOVIET UNION, pubDate=2020, director=로리 랜들러|, actor=, userRating=0.00",
        director = "",
        actor = "",
        userRating = "",
        pubDate = "",
        isBookmark = true
    )

    val movieItemBookmarkIsFalse: NaverEntity.Movie = NaverEntity.Movie(
        title = "굿바이, 나의 <b>소련</b>",
        link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=204441",
        image = "https://ssl.pstatic.net/imgmovie/mdi/mit110/2044/204441_P01_141237.JPG",
        subtitle = "GOODBYE, SOVIET UNION, pubDate=2020, director=로리 랜들러|, actor=, userRating=0.00",
        director = "",
        actor = "",
        userRating = "",
        pubDate = "",
        isBookmark = false
    )

    val naverEntityList:List<NaverEntity> = movieList.map { it as NaverEntity }

    val movieResponse = MovieResponse(
        display = 4, movieList = movieList, start = 1, total = 4, lastBuildDate = ""
    )

    val keyword:String = "소련"
}