package com.jm.moviesearch.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

sealed class NaverEntity {

    @Parcelize
    data class Movie(
        @SerializedName("title") val title: String,
        @SerializedName("link") val link: String,
        @SerializedName("image") val image: String,
        @SerializedName("subtitle") val subtitle: String,
        @SerializedName("pubDate") val pubDate: String,
        @SerializedName("director") val director: String,
        @SerializedName("actor") val actor: String,
        @SerializedName("userRating") val userRating: String,
        var isBookmark: Boolean = false
    ): NaverEntity(), Parcelable {
        fun toBookmark(): Bookmark =
            Bookmark(
                title = title,
                director = director,
                actor = actor,
                image = image,
                link = link,
                pubDate = pubDate,
                subtitle = subtitle,
                userRating = userRating
            )

        override fun equals(movie: Any?): Boolean {
            return when (movie) {
                is Movie -> {
                    this.title == movie.title &&
                            this.image == movie.image &&
                            this.link == movie.link
                }
                else -> false
            }
        }
    }

    @Entity(tableName = "Bookmark")
    @Parcelize
    data class Bookmark(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @SerializedName("title") val title: String,
        @SerializedName("link") val link: String,
        @SerializedName("image") val image: String,
        @SerializedName("subtitle") val subtitle: String,
        @SerializedName("pubDate") val pubDate: String,
        @SerializedName("director") val director: String,
        @SerializedName("actor") val actor: String,
        @SerializedName("userRating") val userRating: String
    ): NaverEntity(), Parcelable {
        fun toMovie(): Movie =
            Movie(
                title = title,
                director = director,
                actor = actor,
                image = image,
                link = link,
                pubDate = pubDate,
                subtitle = subtitle,
                userRating = userRating
            )

        override fun equals(bookmark: Any?): Boolean {
            return when (bookmark) {
                is Bookmark -> {
                    this.title == bookmark.title &&
                            this.image == bookmark.image &&
                            this.link == bookmark.link
                }
                else -> false
            }
        }
    }

}


