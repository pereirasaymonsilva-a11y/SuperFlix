package com.superflix.app.data.models

// Modelo principal para filmes/séries
data class Movie(
    val id: String,
    val title: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val overview: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val year: String? = null,
    val runtime: Int? = null,
    val genres: List<Genre>? = null
)

data class Genre(
    val id: Int,
    val name: String
)

enum class MediaType {
    MOVIE, TV, ANIME, DORAMA
}

// Para dados da API
data class MovieDetails(
    val id: String,
    val title: String,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val year: String? = null,
    val runtime: Int? = null,
    val genres: List<Genre>? = null
)