package com.example.cinematch.ui.results

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinematch.data.model.Movie
import com.example.cinematch.data.repository.MovieRepository
import com.example.cinematch.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResultsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(application)

    private val _uiState = MutableStateFlow<Result<List<Movie>>>(Result.Loading)
    val uiState: StateFlow<Result<List<Movie>>> = _uiState.asStateFlow()

    // Track current page to enable sequential refresh
    private var currentPage = 1

    fun getRecommendations(genre: String) {
        viewModelScope.launch {
            _uiState.value = Result.Loading
            val result = repository.getRecommendationsByGenre(genre, page = currentPage)
            _uiState.value = result
        }
    }

    fun refreshRecommendations(genre: String) {
        // Increment page, wrapping back to 1 after 5 to prevent empty results for smaller categories
        currentPage = if (currentPage >= 5) 1 else currentPage + 1
        getRecommendations(genre)
    }
}
