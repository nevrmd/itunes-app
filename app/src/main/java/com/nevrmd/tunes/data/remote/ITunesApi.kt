package com.nevrmd.tunes.data.remote

import com.nevrmd.tunes.data.remote.dto.ITunesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {

    @GET("search")
    suspend fun searchMedia(
        @Query("term") term: String,
        @Query("country") country: String = "US",
        @Query("media") media: String = "all",
        @Query("limit") limit: Int = 50,
        @Query("lang") language: String = "en_us",
        @Query("explicit") explicit: String = "Yes"
    ): ITunesResponseDto

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }
}