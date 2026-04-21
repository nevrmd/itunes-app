package com.nevrmd.tunes.domain.model

enum class MediaType(val media: String, val entity: String) {
    ALL("all", ""),
    MUSIC("music", "song"),
    MOVIE("movie", "movie"),
    PODCAST("podcast", "podcast"),
    MUSIC_VIDEO("musicVideo", "musicVideo"),
    AUDIOBOOK("audiobook", "audiobook"),
    SHORT_FILM("shortFilm", "shortFilm"),
    TV_SHOW("tvShow", "tvEpisode"),
    SOFTWARE("software", "software"),
    EBOOK("ebook", "ebook");

    fun toDisplayName(): String = name.lowercase()
        .replace("_", " ")
        .split(" ")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}