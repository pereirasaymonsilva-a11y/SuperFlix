package com.superflix.app.data.database

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT 50")
    suspend fun getHistory(): List<HistoryEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToHistory(history: HistoryEntity)
    
    @Delete
    suspend fun removeFromHistory(history: HistoryEntity)
    
    @Query("DELETE FROM history")
    suspend fun clearHistory()
}