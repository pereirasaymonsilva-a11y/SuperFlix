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
    val runtime: Int? = null,
    val year: String? = null
)

data class Genre(
    val id: Int,
    val name: String
)

enum class MediaType {
    MOVIE, TV, ANIME, DORAMA
}

// Para histórico e favoritos
data class FavoriteItem(
    val id: String,
    val type: MediaType,
    val title: String = "",
    val posterPath: String? = null
)

data class HistoryItem(
    val id: String,
    val type: MediaType,
    val title: String = "",
    val timestamp: Long = System.currentTimeMillis()
)