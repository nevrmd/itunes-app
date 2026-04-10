package com.nevrmd.tunes.domain.use_case

import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.repository.MediaRepository
import javax.inject.Inject

class SearchMediaUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    suspend operator fun invoke(term: String): Result<List<MediaItem>> {
        if (term.isBlank()) {
            return Result.success(emptyList())
        }

        return repository.searchMedia(term)
    }
}