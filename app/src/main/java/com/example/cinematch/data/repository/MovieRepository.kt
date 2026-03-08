package com.example.cinematch.data.repository

import android.content.Context
import com.example.cinematch.data.api.RetrofitClient
import com.example.cinematch.data.db.AppDatabase
import com.example.cinematch.data.db.MovieEntity
import com.example.cinematch.data.model.Movie
import com.example.cinematch.data.model.MovieDetail
import com.example.cinematch.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieRepository(private val context: Context) {

    private val apiService = RetrofitClient.apiService
    private val movieDao = AppDatabase.getDatabase(context).movieDao()

    suspend fun getRecommendationsByGenre(genre: String, page: Int = 1): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
                // Since OMDb doesn't strictly search by genre, we use the genre string as the search query
                // and grab the first 10 results. For a more robust app, a TMDB API would be much better
                // for literal genre-based queries.
                // We also pass the page parameter to support randomizing suggestions.
                val response = apiService.searchMovies(query = genre, page = page)
                if (response.response == "True" && response.search != null) {
                    Result.Success(response.search)
                } else {
                    Result.Error(Exception(response.response ?: "Unknown API Error"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun getMovieDetails(imdbId: String): Result<MovieDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val detail = apiService.getMovieDetails(imdbId = imdbId)
                if (detail.response == "True") {
                    Result.Success(detail)
                } else {
                    Result.Error(Exception(detail.error ?: "Failed to get movie details"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    suspend fun saveMovieToWatchlist(movie: MovieDetail, rating: Float) {
        withContext(Dispatchers.IO) {
            val entity = MovieEntity(
                imdbId = movie.imdbId,
                title = movie.title,
                posterUrl = movie.poster,
                genre = movie.genre,
                userRating = rating
            )
            movieDao.insertOrUpdateMovie(entity)
        }
    }

    fun getAllSavedMovies(): Flow<List<MovieEntity>> {
        return movieDao.getAllSavedMovies()
    }

    fun getMovieById(id: String): Flow<MovieEntity?> {
        return movieDao.getMovieById(id)
    }

    suspend fun getHighlyRatedGenres(): List<String> {
        return withContext(Dispatchers.IO) {
            movieDao.getHighlyRatedGenres()
        }
    }
}
