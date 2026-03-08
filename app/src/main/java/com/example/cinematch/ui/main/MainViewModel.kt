package com.example.cinematch.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cinematch.data.repository.MovieRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(application)
}
