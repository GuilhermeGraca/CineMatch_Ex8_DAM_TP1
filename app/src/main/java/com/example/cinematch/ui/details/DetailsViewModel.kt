package com.example.cinematch.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinematch.data.model.MovieDetail
import com.example.cinematch.data.repository.MovieRepository
import com.example.cinematch.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(application)

    private val _uiState = MutableStateFlow<Result<MovieDetail>>(Result.Loading)
    val uiState: StateFlow<Result<MovieDetail>> = _uiState.asStateFlow()

    fun getMovieDetails(imdbId: String) {
        viewModelScope.launch {
            _uiState.value = Result.Loading
            val result = repository.getMovieDetails(imdbId)
            _uiState.value = result
        }
    }

    fun saveToWatchlist(movie: MovieDetail, rating: Float, onSaved: () -> Unit) {
        viewModelScope.launch {
            repository.saveMovieToWatchlist(movie, rating)
            onSaved()
        }
    }
}
