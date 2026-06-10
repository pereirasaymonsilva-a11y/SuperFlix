package com.superflix.app.data.models

data class Movie(
    val id: String = "",
    val title: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val year: String = "",
    val genres: List<String> = emptyList(),
    val rating: Double = 0.0,
    val isFavorite: Boolean = false
)