package com.nevrmd.tunes.domain.use_case

import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.repository.MediaRepository
import javax.inject.Inject

class GetMediaItemUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    suspend operator fun invoke(id: Long): MediaItem? {
        return repository.getMediaItemById(id)
    }
}