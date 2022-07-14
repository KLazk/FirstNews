package com.shokworks.firstnews.dbRoom

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TFavNews::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {
    abstract fun scDao(): Dao
}