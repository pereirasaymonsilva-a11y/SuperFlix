// utils/Constants.kt
package com.superflix.app.utils

import com.superflix.app.data.models.MovieDetails

object Constants {
    private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    
    fun getPosterUrl(id: String, size: String = "w500"): String {
        return "${TMDB_IMAGE_BASE_URL}$size/$id.jpg"
    }
    
    fun getBackdropUrl(id: String, size: String = "w1280"): String {
        return "${TMDB_IMAGE_BASE_URL}$size/$id.jpg"
    }
    
    // Fallback para quando não temos os detalhes da API
    suspend fun getMovieDetailsFromTMDB(id: String): MovieDetails {
        // Aqui você pode implementar uma chamada real à API do TMDB
        // ou retornar dados mockados
        return MovieDetails(
            id = id,
            title = "Título do Filme",
            overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            posterPath = id,
            backdropPath = id,
            voteAverage = 7.5,
            year = "2024",
            runtime = 120,
            genres = emptyList()
        )
    }
}

// res/values/strings.xml