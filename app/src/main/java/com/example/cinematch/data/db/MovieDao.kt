package com.example.cinematch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getAllSavedMovies(): Flow<List<MovieEntity>>
    
    @Query("SELECT * FROM movies WHERE imdbId = :id LIMIT 1")
    fun getMovieById(id: String): Flow<MovieEntity?>

    // Used for recommendations logic
    @Query("SELECT genre FROM movies WHERE userRating >= 4.0")
    suspend fun getHighlyRatedGenres(): List<String>
}
