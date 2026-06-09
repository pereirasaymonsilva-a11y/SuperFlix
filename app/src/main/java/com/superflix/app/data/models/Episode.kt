package com.superflix.app.data.models

data class Episode(
    val episodeNumber: Int,
    val title: String,
    val overview: String = "",
    val stillPath: String = "",
    val airDate: String = "",
    val watched: Boolean = false
)