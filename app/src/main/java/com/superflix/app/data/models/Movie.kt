// data/models/Movie.kt
package com.superflix.app.data.models

data class Movie(
    val id: String,
    val title: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val overview: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null,
    val genres: List<Genre>? = null,
    val runtime: Int? = null
)

data class Genre(
    val id: Int,
    val name: String
)

enum class MediaType {
    MOVIE, TV, ANIME, DORAMA
}