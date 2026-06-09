// ui/screens/home/HomeViewModel.kt
package com.superflix.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.api.ApiService
import com.superflix.app.data.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val apiService: ApiService
) : ViewModel() {
    
    private val _movieIds = MutableStateFlow<List<String>>(emptyList())
    val movieIds: StateFlow<List<String>> = _movieIds.asStateFlow()
    
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadPopularMovies()
    }
    
    fun loadPopularMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val ids = apiService.getMovieIds("filme")
            _movieIds.value = ids
            
            // Carrega detalhes dos primeiros 20 filmes
            val movies = ids.take(20).mapNotNull { id ->
                apiService.getMovieDetails(id)
            }
            _movies.value = movies
            _isLoading.value = false
        }
    }
}