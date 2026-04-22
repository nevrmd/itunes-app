package com.nevrmd.tunes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nevrmd.tunes.data.local.entities.SearchResultEntity

@Database(
    entities = [SearchResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
}