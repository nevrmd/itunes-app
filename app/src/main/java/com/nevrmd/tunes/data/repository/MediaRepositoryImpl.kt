package com.nevrmd.tunes.data.repository

import com.nevrmd.tunes.data.mapper.toMediaItem
import com.nevrmd.tunes.data.remote.ITunesApi
import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.repository.MediaRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaRepositoryImpl @Inject constructor(
    private val api: ITunesApi
) : MediaRepository {

    override suspend fun searchMedia(term: String): Result<List<MediaItem>> {
        return withContext(Dispatchers.IO) { // Moves work to background thread
            try {
                val response = api.searchMedia(term = term)
                Result.success(response.results.map { it.toMediaItem() })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}