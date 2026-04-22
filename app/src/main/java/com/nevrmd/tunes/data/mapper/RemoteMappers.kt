package com.nevrmd.tunes.data.mapper

import com.nevrmd.tunes.data.local.entities.SearchResultEntity
import com.nevrmd.tunes.data.remote.dto.ITunesMediaDto

fun ITunesMediaDto.toEntity(query: String): SearchResultEntity {
    val originalUrl = artworkUrl100 ?: ""
    val highResUrl = originalUrl.replace("100x100bb", "600x600bb")
        .ifEmpty { originalUrl.replace("100x100", "600x600") }

    return SearchResultEntity(
        id = trackId ?: collectionId ?: artistId ?: this.hashCode().toLong(),
        title = trackName ?: collectionName ?: "Unknown",
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
        mediaType = kind ?: wrapperType ?: "unknown",
        searchQuery = query
    )
}