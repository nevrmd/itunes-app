package com.nevrmd.tunes.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ITunesResponseDto(
    @SerialName("resultCount") val resultCount: Int,
    @SerialName("results") val results: List<ITunesMediaDto>
)

@Serializable
data class ITunesMediaDto(
    @SerialName("trackId") val trackId: Long? = null,
    @SerialName("artistName") val artistName: String? = null,
    @SerialName("trackName") val trackName: String? = null,
    @SerialName("collectionName") val collectionName: String? = null,
    @SerialName("artworkUrl100") val artworkUrl100: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null,
    @SerialName("kind") val kind: String? = null,
    @SerialName("country") val country: String? = null
)