// data/models/MediaItem.kt
package com.superflix.app.data.models

enum class MediaType {
    MOVIE, SERIES, ANIME, DORAMA
}

data class MediaItem(
    val id: String,
    val title: String = "",
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val overview: String? = null,
    val voteAverage: Double? = null,
    val year: String? = null,
    val type: MediaType = MediaType.MOVIE
)

data class MovieDetails(
    val id: String,
    val title: String,
    val originalTitle: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val releaseDate: String? = null,
    val runtime: Int? = null,
    val genres: List<Genre>? = null,
    val year: String? = null
)

data class Genre(
    val id: Int,
    val name: String
)

data class SearchResult(
    val id: String,
    val title: String,
    val posterPath: String?,
    val type: MediaType
)