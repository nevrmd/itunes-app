package com.nevrmd.tunes.presentation.detail

import com.nevrmd.tunes.domain.model.MediaItem

data class DetailState(
    val mediaItem: MediaItem? = null,
    val isPlaying: Boolean = false,
    val isLoadingAudio: Boolean = false,
    val progress: Float = 0f
)