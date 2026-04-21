package com.nevrmd.tunes.presentation.search

import com.nevrmd.tunes.domain.model.MediaType

sealed interface SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent
    data class OnMediaChipSelect(val mediaType: MediaType) : SearchEvent
    data class OnItemClick(val itemId: Long) : SearchEvent
}