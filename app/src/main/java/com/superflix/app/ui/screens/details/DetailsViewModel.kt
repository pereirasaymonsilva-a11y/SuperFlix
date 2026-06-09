package com.superflix.app.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.superflix.app.data.models.MediaType
import com.superflix.app.data.models.MovieDetails

class DetailsViewModel : ViewModel() {
    val movieDetails = mutableStateOf<MovieDetails?>(null)
    val isLoading = mutableStateOf(true)
    val isFavorite = mutableStateOf(false)
    
    fun loadMovieDetails(movieId: String, mediaType: MediaType) {
        isLoading.value = false
        movieDetails.value = MovieDetails(
            id = movieId,
            title = "Filme $movieId",
            overview = "Detalhes do filme",
            year = "2024",
            voteAverage = 7.5
        )
    }
    
    fun checkFavorite(movieId: String) {}
    fun toggleFavorite() {
        isFavorite.value = !isFavorite.value
    }
}