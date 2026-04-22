package com.nevrmd.tunes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nevrmd.tunes.data.local.entities.SearchResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResults(results: List<SearchResultEntity>)

    @Query("SELECT * FROM search_results WHERE searchQuery = :query ORDER BY cachedAt DESC")
    fun getResultsForQuery(query: String): Flow<List<SearchResultEntity>>

    @Query("SELECT * FROM search_results WHERE id = :id LIMIT 1")
    suspend fun getMediaItemById(id: Long): SearchResultEntity?

    @Query("SELECT * FROM search_results WHERE searchQuery = :query")
    suspend fun getResultsForQuerySync(query: String): List<SearchResultEntity> // for catch block

    @Query("""
        DELETE FROM search_results
        WHERE id NOT IN (
            SELECT id FROM search_results
            ORDER BY cachedAt DESC
            LIMIT 200
        )
    """)
    suspend fun trimCache()

    @Query("DELETE FROM search_results")
    fun clearAll()
}