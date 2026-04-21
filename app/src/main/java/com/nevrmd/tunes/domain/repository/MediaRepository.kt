package com.nevrmd.tunes.domain.repository

import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.model.MediaType

interface MediaRepository {
    suspend fun searchMedia(query: String, type: MediaType): Result<List<MediaItem>>
    fun getMediaItemById(id: Long): MediaItem?
}