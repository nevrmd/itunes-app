package com.nevrmd.tunes.data.mapper

import com.nevrmd.tunes.data.remote.dto.ITunesMediaDto
import com.nevrmd.tunes.domain.model.MediaItem

fun ITunesMediaDto.toMediaItem(): MediaItem {
    val originalUrl = artworkUrl100 ?: ""
    val highResUrl = originalUrl.replace("100x100bb", "600x600bb")
        .ifEmpty { originalUrl.replace("100x100", "600x600") }

    return MediaItem(
        id = trackId ?: collectionId ?: artistId ?: this.hashCode().toLong(),
        title = trackName ?: "Unknown",
        artist = artistName ?: "Unknown Artist",
        imageUrl = highResUrl,
        previewUrl = previewUrl ?: "",
        collectionName = collectionName,
        trackTimeMillis = trackTimeMillis,
        country = country,
        currency = currency,
        trackPrice = trackPrice,
        primaryGenreName = primaryGenreName,
        releaseDate = releaseDate,
        trackExplicitness = trackExplicitness,
        mediaType = kind ?: wrapperType ?: "unknown"
    )
}