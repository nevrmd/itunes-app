package com.nevrmd.tunes.presentation.detail

sealed interface DetailEvent {
    data object TogglePlay : DetailEvent
    data object OnBackClick : DetailEvent
}