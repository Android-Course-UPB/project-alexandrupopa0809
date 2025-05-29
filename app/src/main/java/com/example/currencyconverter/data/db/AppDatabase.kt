package com.example.currencyconverter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.data.dao.ConversionDao
import com.example.currencyconverter.data.model.ConversionEntity

@Database(entities = [ConversionEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionDao(): ConversionDao
}
