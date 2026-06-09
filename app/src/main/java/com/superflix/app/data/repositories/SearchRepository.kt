package com.superflix.app.data.repositories

import com.superflix.app.data.api.SuperFlixApi
import com.superflix.app.data.models.SearchResult

class SearchRepository(
    private val api: SuperFlixApi
) {
    suspend fun search(query: String): List<SearchResult> {
        if (query.length < 2) return emptyList()
        return try {
            api.search(query)
        } catch (e: Exception) {
            emptyList()
        }
    }
}