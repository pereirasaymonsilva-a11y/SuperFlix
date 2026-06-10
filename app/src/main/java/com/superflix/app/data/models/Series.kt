package com.superflix.app.data.models

data class Series(
    val id: String = "",
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val year: String = "",
    val genres: List<String> = emptyList(),
    val seasons: List<Season> = emptyList(),
    val totalSeasons: Int = 0,
    val isFavorite: Boolean = false
)