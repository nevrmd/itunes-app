package com.nevrmd.tunes.di

import com.nevrmd.tunes.data.remote.ITunesApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideITunesApi(): ITunesApi {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl(ITunesApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ITunesApi::class.java)
    }
}