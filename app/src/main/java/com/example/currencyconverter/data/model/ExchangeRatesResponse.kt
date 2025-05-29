package com.example.currencyconverter.data.model

data class ExchangeRatesResponse(
    val date: String,
    val base: String,
    val rates: Map<String, String>
)

//{
//    "date": "2023-03-21 12:43:00+00",
//    "base": "USD",
//    "rates": {
//    "EUR": "0.9",
//    "RON": "4.94"
//}
//}
