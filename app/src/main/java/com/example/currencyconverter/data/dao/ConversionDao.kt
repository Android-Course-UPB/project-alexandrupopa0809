package com.example.currencyconverter.data.dao

import androidx.room.*
import com.example.currencyconverter.data.model.ConversionEntity

@Dao
interface ConversionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(conversion: ConversionEntity)

    @Query("SELECT * FROM conversion_history ORDER BY timestamp DESC LIMIT 10")
    suspend fun getLast10(): List<ConversionEntity>

    @Query("DELETE FROM conversion_history")
    suspend fun clearAll()
}