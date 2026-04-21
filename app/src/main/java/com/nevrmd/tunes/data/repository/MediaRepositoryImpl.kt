package com.nevrmd.tunes.data.repository

import com.nevrmd.tunes.data.mapper.toMediaItem
import com.nevrmd.tunes.data.remote.ITunesApi
import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.model.MediaType
import com.nevrmd.tunes.domain.repository.MediaRepository
import jakarta.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val api: ITunesApi
) : MediaRepository {
    private var cachedResults = listOf<MediaItem>()

    override suspend fun searchMedia(
        query: String,
        type: MediaType
    ): Result<List<MediaItem>> {
        return try {
            val response = api.searchMedia(
                term = query,
                media = type.media,
                entity = type.entity
            )
            val domainItems = response.results.map { it.toMediaItem() }
            cachedResults = domainItems

            Result.success(domainItems)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getMediaItemById(id: Long): MediaItem? = cachedResults.find { it.id == id }
}