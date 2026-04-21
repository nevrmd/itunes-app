package com.nevrmd.tunes.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Search : Screen

    @Serializable
    data class Detail(
        val id: Long
    ) : Screen
}