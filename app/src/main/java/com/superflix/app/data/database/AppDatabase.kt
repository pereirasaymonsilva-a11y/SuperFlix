// data/database/AppDatabase.kt
package com.superflix.app.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(
    entities = [FavoriteEntity::class, HistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "superflix_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// data/database/FavoriteDao.kt
package com.superflix.app.data.database

import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)
    
    @Delete
    suspend fun removeFavorite(favorite: FavoriteEntity)
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}

// data/database/HistoryDao.kt
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

// data/database/Entities.kt
package com.superflix.app.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.superflix.app.data.models.MediaType

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val posterPath: String?,
    val type: MediaType,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val posterPath: String?,
    val type: MediaType,
    val progress: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

// data/database/Converters.kt
package com.superflix.app.data.database

import androidx.room.TypeConverter
import com.superflix.app.data.models.MediaType

class Converters {
    @TypeConverter
    fun fromMediaType(type: MediaType): String {
        return type.name
    }
    
    @TypeConverter
    fun toMediaType(type: String): MediaType {
        return MediaType.valueOf(type)
    }
}