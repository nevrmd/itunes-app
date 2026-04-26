package com.nevrmd.tunes.data.repository

import com.nevrmd.tunes.data.local.SearchDao
import com.nevrmd.tunes.data.mapper.toEntity
import com.nevrmd.tunes.data.mapper.toMediaItem
import com.nevrmd.tunes.data.remote.ITunesApi
import com.nevrmd.tunes.domain.model.MediaItem
import com.nevrmd.tunes.domain.model.MediaType
import com.nevrmd.tunes.domain.repository.MediaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val api: ITunesApi,
    private val dao: SearchDao,
    private val ioDispatcher: CoroutineDispatcher
) : MediaRepository {

    override fun searchMedia(
        query: String,
        type: MediaType
    ): Flow<Result<List<MediaItem>>> = flow {
        val localResults = dao.getResultsForQuerySync(query).map { it.toMediaItem() }
        emit(Result.success(localResults))

        try {
            val response = api.searchMedia(
                term = query,
                media = type.media,
                entity = type.entity
            )
            val entities = response.results.map { it.toEntity(query) }

            dao.insertResults(entities)
            dao.trimCache()

            emit(Result.success(entities.map { it.toMediaItem() }))
        } catch (e: Exception) {
            if (localResults.isEmpty()) {
                emit(Result.failure(e))
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun getMediaItemById(id: Long): MediaItem? {
        return dao.getMediaItemById(id)?.toMediaItem()
    }
}