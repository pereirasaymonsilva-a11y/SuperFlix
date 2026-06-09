package com.superflix.app.data.repositories

import com.superflix.app.data.api.SuperFlixApi
import com.superflix.app.data.cache.LocalCache
import com.superflix.app.data.models.Movie

class MovieRepository(
    private val api: SuperFlixApi,
    private val cache: LocalCache
) {
    suspend fun getMovies(category: String = "filme", useCache: Boolean = true): List<Movie> {
        if (useCache) {
            val cached = cache.getMovies(category)
            if (cached.isNotEmpty()) return cached
        }
        
        return try {
            val movies = api.getMovies(category)
            cache.saveMovies(category, movies)
            movies
        } catch (e: Exception) {
            cache.getMovies(category)
        }
    }

    suspend fun getTrending(): List<Movie> {
        return getMovies("filme").take(10)
    }

    suspend fun getMovieById(id: String): Movie? {
        return getMovies().find { it.id == id }
    }
}