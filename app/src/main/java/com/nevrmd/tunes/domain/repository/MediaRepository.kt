package com.nevrmd.tunes.domain.repository

import com.nevrmd.tunes.domain.model.MediaItem

interface MediaRepository {
    suspend fun searchMedia(term: String): Result<List<MediaItem>>
}