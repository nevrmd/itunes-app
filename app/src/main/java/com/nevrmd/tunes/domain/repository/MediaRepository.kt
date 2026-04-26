package com.nevrmd.tunes.domain.repository

import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun searchMedia(query: String, type: MediaType): Flow<Result<List<MediaItem>>>
    suspend fun getMediaItemById(id: Long): MediaItem?
}