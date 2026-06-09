package com.superflix.app.data.repositories

import com.superflix.app.data.api.SuperFlixApi
import com.superflix.app.data.cache.LocalCache
import com.superflix.app.data.models.Series

class SeriesRepository(
    private val api: SuperFlixApi,
    private val cache: LocalCache
) {
    suspend fun getSeries(category: String = "serie"): List<Series> {
        val cached = cache.getSeries(category)
        if (cached.isNotEmpty()) return cached
        
        return try {
            val series = api.getSeries(category)
            cache.saveSeries(category, series)
            series
        } catch (e: Exception) {
            cache.getSeries(category)
        }
    }

    suspend fun getSeriesById(id: String): Series? {
        return getSeries().find { it.id == id }
    }
}