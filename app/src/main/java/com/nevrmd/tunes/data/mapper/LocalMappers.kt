package com.nevrmd.tunes.data.mapper

import com.nevrmd.tunes.data.local.entities.SearchResultEntity
import com.nevrmd.tunes.domain.model.MediaItem

fun SearchResultEntity.toMediaItem() = MediaItem(
    id = id,
    title = title,
    artist = artist,
    imageUrl = imageUrl,
    previewUrl = previewUrl,
    mediaType = mediaType,
    collectionName = collectionName,
    trackTimeMillis = trackTimeMillis,
    country = country,
    currency = currency,
    trackPrice = trackPrice,
    primaryGenreName = primaryGenreName,
    releaseDate = releaseDate,
    trackExplicitness = trackExplicitness
)