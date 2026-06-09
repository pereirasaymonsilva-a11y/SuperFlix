package com.superflix.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.api.ApiService
import com.superflix.app.data.models.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiService = ApiService()
    
    private val _movies = MutableStateFlow<List<MovieDetails>>(emptyList())
    val movies: StateFlow<List<MovieDetails>> = _movies
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    init {
        loadMovies()
    }
    
    fun loadMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            println("🚀 Carregando filmes...")
            
            val ids = apiService.getMovieIds("filme")
            println("📋 IDs obtidos: $ids")
            
            val moviesList = ids.take(10).mapNotNull { id ->
                val movie = apiService.getMovieDetails(id)
                println("🎬 Filme carregado: ${movie?.title}")
                movie
            }
            
            println("✅ Total de filmes carregados: ${moviesList.size}")
            _movies.value = moviesList
            _isLoading.value = false
        }
    }
}