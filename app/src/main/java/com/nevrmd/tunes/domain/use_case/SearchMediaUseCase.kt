package com.nevrmd.tunes.domain.use_case

import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.model.MediaType
import com.nevrmd.tunes.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SearchMediaUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    suspend operator fun invoke(query: String, type: MediaType): Flow<Result<List<MediaItem>>> {
        if (query.isBlank()) {
            return flowOf(Result.success(emptyList()))
        }

        return repository.searchMedia(query, type)
    }
}