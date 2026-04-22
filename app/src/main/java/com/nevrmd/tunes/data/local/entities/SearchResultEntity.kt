package com.nevrmd.tunes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_results")
data class SearchResultEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val previewUrl: String,
    val mediaType: String,
    val collectionName: String?,
    val trackTimeMillis: Long?,
    val country: String?,
    val currency: String?,
    val trackPrice: Double?,
    val primaryGenreName: String?,
    val releaseDate: String?,
    val trackExplicitness: String?,
    // for cache logic
    val searchQuery: String,
    val cachedAt: Long = System.currentTimeMillis()
)