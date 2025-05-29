package com.example.currencyconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversion_history")
data class ConversionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double,
    val amount: Double,
    val timestamp: Long = System.currentTimeMillis()
)
