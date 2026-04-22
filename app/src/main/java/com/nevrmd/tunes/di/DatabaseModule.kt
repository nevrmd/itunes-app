package com.nevrmd.tunes.di

import android.content.Context
import androidx.room.Room
import com.nevrmd.tunes.data.local.AppDatabase
import com.nevrmd.tunes.data.local.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "itunes_cache.db"
        ).build()
    }

    @Provides
    fun provideSearchDao(db: AppDatabase): SearchDao {
        return db.searchDao()
    }
}