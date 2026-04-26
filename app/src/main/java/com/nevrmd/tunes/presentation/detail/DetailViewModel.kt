package com.nevrmd.tunes.presentation.detail

import android.content.ComponentName
import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.nevrmd.tunes.data.service.MediaService
import com.nevrmd.tunes.domain.use_case.GetMediaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import androidx.core.net.toUri

@HiltViewModel
class DetailViewModel @OptIn(UnstableApi::class)
@Inject constructor(
    private val getMediaItemUseCase: GetMediaItemUseCase,
    @ApplicationContext context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private var browserFuture: ListenableFuture<MediaController>? = null
    private val controller: MediaController? get() = if (browserFuture?.isDone == true) browserFuture?.get() else null

    private var pendingUrl: String? = null
    private var tickerJob: Job? = null

    init {
        val sessionToken = SessionToken(context, ComponentName(context, MediaService::class.java))
        browserFuture = MediaController.Builder(context, sessionToken).buildAsync()

        browserFuture?.addListener({
            // check if already canceled
            if (browserFuture?.isCancelled == true) return@addListener

            setupControllerListener()
            pendingUrl?.let {
                prepareAudio(it)
                pendingUrl = null
            }
        }, MoreExecutors.directExecutor())
    }

    fun loadItem(id: Long) {
        viewModelScope.launch {
            val item = getMediaItemUseCase(id)
            _state.update { it.copy(mediaItem = item) }

            item?.let {
                if (controller == null) {
                    pendingUrl = it.previewUrl
                } else {
                    prepareAudio(it.previewUrl)
                }
            }
        }
    }

    private fun prepareAudio(url: String) {
        val item = _state.value.mediaItem ?: return

        controller?.let { player ->
            player.stop()
            player.clearMediaItems()
            _state.update { it.copy(isPlaying = false) }

            val mediaItem = MediaItem.Builder()
                .setUri(url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(item.title)
                        .setArtist(item.artist)
                        .setArtworkUri(item.imageUrl.toUri())
                        .build()
                ).build()

            player.setMediaItem(mediaItem)
            player.prepare()
            _state.update { it.copy(isLoadingAudio = true) }
        }
    }

    fun onEvent(event: DetailEvent) {
        val player = controller ?: return
        when (event) {
            is DetailEvent.TogglePlay -> {
                if (player.isPlaying) player.pause() else player.play()
            }

            DetailEvent.OnBackClick -> stopAndClear()
        }
    }

    private fun setupControllerListener() {
        controller?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _state.update { it.copy(isPlaying = isPlaying) }
                if (isPlaying) startProgressTicker() else stopProgressTicker()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                _state.update {
                    it.copy(isLoadingAudio = playbackState == Player.STATE_BUFFERING)
                }
            }
        })
    }

    private fun startProgressTicker() {
        tickerJob?.cancel()
        tickerJob = viewModelScope.launch {
            while (isActive) {
                controller?.let { player ->
                    if (player.duration > 0) {
                        val currentProgress =
                            player.currentPosition.toFloat() / player.duration.toFloat()
                        _state.update { it.copy(progress = currentProgress) }
                    }
                }
                delay(200)
            }
        }
    }

    private fun stopProgressTicker() {
        tickerJob?.cancel()
    }

    fun seekTo(percent: Float) {
        controller?.let { player ->
            if (player.duration > 0) {
                val seekPosition = (player.duration * percent).toLong()
                player.seekTo(seekPosition)
                _state.update { it.copy(progress = percent) }
            }
        }
    }

    fun stopAndClear() {
        controller?.stop()
        stopProgressTicker()
    }

    override fun onCleared() {
        super.onCleared()
        stopProgressTicker()
        pendingUrl = null
        browserFuture?.cancel(true)
        browserFuture?.let { MediaController.releaseFuture(it) }
    }
}