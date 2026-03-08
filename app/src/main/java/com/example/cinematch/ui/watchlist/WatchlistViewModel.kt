package com.example.cinematch.ui.watchlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cinematch.data.db.MovieEntity
import com.example.cinematch.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class WatchlistViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(application)

    val savedMovies: Flow<List<MovieEntity>> = repository.getAllSavedMovies()

}
