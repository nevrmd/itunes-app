package com.nevrmd.tunes.domain.model

data class MediaItem(
    val id: Long,
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
    val trackExplicitness: String?
)