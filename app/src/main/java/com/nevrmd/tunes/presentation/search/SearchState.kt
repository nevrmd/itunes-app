package com.nevrmd.tunes.presentation.search

import com.nevrmd.tunes.domain.model.MediaItem

data class SearchState(
    val query: String = "",
    val results: List<MediaItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)