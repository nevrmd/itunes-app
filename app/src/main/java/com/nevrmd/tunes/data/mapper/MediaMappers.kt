package com.nevrmd.tunes.data.mapper

import com.nevrmd.tunes.data.remote.dto.ITunesMediaDto
import com.nevrmd.tunes.domain.model.MediaItem

fun ITunesMediaDto.toMediaItem(): MediaItem {
    return MediaItem(
        id = trackId ?: 0L,
        title = trackName ?: collectionName ?: "Unknown",
        artist = artistName ?: "Unknown Artist",
        imageUrl = artworkUrl100 ?: "",
        previewUrl = previewUrl ?: "",
        mediaType = kind ?: "unknown"
    )
}