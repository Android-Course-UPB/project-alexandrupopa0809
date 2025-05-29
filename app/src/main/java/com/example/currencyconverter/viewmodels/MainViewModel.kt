package com.example.currencyconverter.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.utils.Strings
import com.example.currencyconverter.data.remote.RetrofitInstance
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.currencyconverter.data.db.AppDatabase
import com.example.currencyconverter.data.model.ConversionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "currency-db"
    ).build()

    private val conversionDao = db.conversionDao()

    var fromCurrency by mutableStateOf("USD")
    var toCurrency by mutableStateOf("EUR")
    var amountInput by mutableStateOf("")
    var result by mutableStateOf("")
    var history = mutableStateListOf<String>()

    var rates by mutableStateOf<Map<String, String>>(emptyMap())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    val currencies: List<String>
        get() = rates.keys.sorted()

    fun fetchRates(apiKey: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null
                val response = RetrofitInstance.api.getLatestRates(apiKey)
                rates = response.rates
            } catch (e: Exception) {
                errorMessage = "Failed to load exchange rates"
            } finally {
                isLoading = false
            }
        }
    }

    fun convert() {
        val amount = amountInput.toDoubleOrNull() ?: return
        val rate = getConversionRate(fromCurrency, toCurrency)
        val converted = amount * rate
        result = "$amount $fromCurrency = ${"%.2f".format(converted)} $toCurrency"

        viewModelScope.launch {
            val entry = ConversionEntity(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                rate = rate,
                amount = amount
            )
            conversionDao.insert(entry)
            Log.d("MainViewModel", "Inserted conversion entry: $entry")
        }
    }

    private fun getConversionRate(from: String, to: String): Double {
        val fromRate = rates[from]?.toDoubleOrNull() ?: return 1.0
        val toRate = rates[to]?.toDoubleOrNull() ?: return 1.0
        return toRate / fromRate
    }

    fun loadHistoryFromDb(language: String) {
        viewModelScope.launch {
            val entries = conversionDao.getLast10()

            withContext(Dispatchers.Main) {
                history.clear()
                history.addAll(entries.map {
                    val resultText = "${it.amount} ${it.fromCurrency} = ${"%.2f".format(it.amount * it.rate)} ${it.toCurrency}"
                    val rateLabel = Strings.get("rate", language)
                    "$resultText ($rateLabel: ${"%.4f".format(it.rate)})"
                })
            }
        }
    }
}
