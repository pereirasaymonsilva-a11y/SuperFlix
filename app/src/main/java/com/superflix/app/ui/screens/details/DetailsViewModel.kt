package com.superflix.app.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.api.ApiService
import com.superflix.app.data.models.MediaType
import com.superflix.app.data.models.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val apiService = ApiService()
    
    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite
    
    fun loadMovieDetails(movieId: String, mediaType: MediaType) {
        viewModelScope.launch {
            _isLoading.value = true
            val details = apiService.getMovieDetails(movieId)
            _movieDetails.value = details
            _isLoading.value = false
        }
    }
    
    fun checkFavorite(movieId: String) {
        // Implementar verificação de favorito
    }
    
    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
    }
}