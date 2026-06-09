package com.superflix.app.data.models

data class Season(
    val seasonNumber: Int,
    val episodes: List<Episode>,
    val posterPath: String = "",
    val overview: String = ""
)