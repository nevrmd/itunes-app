package com.nevrmd.tunes.domain.model

data class MediaItem(
    val id: Long,
    val title: String,
    val artist: String,
    val imageUrl: String,
    val previewUrl: String,
    val mediaType: String
)