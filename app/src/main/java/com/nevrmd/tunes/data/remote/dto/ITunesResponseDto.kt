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
    @SerialName("collectionId") val collectionId: Long? = null,
    @SerialName("artistId") val artistId: Long? = null,
    @SerialName("wrapperType") val wrapperType: String? = null,
    @SerialName("artistName") val artistName: String? = null,
    @SerialName("trackName") val trackName: String? = null,
    @SerialName("collectionName") val collectionName: String? = null,
    @SerialName("artworkUrl100") val artworkUrl100: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null,
    @SerialName("kind") val kind: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("trackPrice") val trackPrice: Double? = null,
    @SerialName("trackTimeMillis") val trackTimeMillis: Long? = null,
    @SerialName("primaryGenreName") val primaryGenreName: String? = null,
    @SerialName("releaseDate") val releaseDate: String? = null,
    @SerialName("trackExplicitness") val trackExplicitness: String? = null
)