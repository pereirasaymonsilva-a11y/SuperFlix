// ui/screens/details/DetailsViewModel.kt
package com.superflix.app.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superflix.app.data.models.MediaItem
import com.superflix.app.data.models.MediaType
import com.superflix.app.data.models.MovieDetails
import com.superflix.app.utils.Constants
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val _movieDetails = mutableStateOf<MovieDetails?>(null)
    val movieDetails = _movieDetails
    
    private val _isLoading = mutableStateOf(true)
    val isLoading = _isLoading
    
    private val _isFavorite = mutableStateOf(false)
    val isFavorite = _isFavorite
    
    fun loadMovieDetails(movieId: String, mediaType: MediaType) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Como a API não tem endpoint de detalhes, usamos TMDB
                val details = Constants.getMovieDetailsFromTMDB(movieId)
                _movieDetails.value = details
            } catch (e: Exception) {
                e.printStackTrace()
                // Fallback: criar detalhes básicos
                _movieDetails.value = MovieDetails(
                    id = movieId,
                    title = "Filme $movieId",
                    overview = "Detalhes não disponíveis no momento.",
                    year = "2024"
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun checkFavorite(movieId: String) {
        // Implementar verificação de favoritos do Room Database
        viewModelScope.launch {
            // _isFavorite.value = repository.isFavorite(movieId)
        }
    }
    
    fun toggleFavorite() {
        viewModelScope.launch {
            if (_isFavorite.value) {
                // repository.removeFromFavorites(movieDetails.value?.id ?: return@launch)
                _isFavorite.value = false
            } else {
                // repository.addToFavorites(MediaItem(...))
                _isFavorite.value = true
            }
        }
    }
}