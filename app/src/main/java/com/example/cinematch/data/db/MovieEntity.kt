package com.example.cinematch.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val imdbId: String,
    val title: String,
    val posterUrl: String,
    val genre: String,
    val userRating: Float = 0f // 0 to 5 stars
)
