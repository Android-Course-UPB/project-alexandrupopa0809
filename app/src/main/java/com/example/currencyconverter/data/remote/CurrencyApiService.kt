package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.model.ExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("latest")
    suspend fun getLatestRates(
        @Query("apikey") apiKey: String,
        @Query("base") base: String = "USD"
    ): ExchangeRatesResponse
}

//curl 'https://api.currencyfreaks.com/v2.0/rates/latest?apikey=YOUR_APIKEY'
//
//{
//    "date": "2023-03-21 12:43:00+00",
//    "base": "USD",
//    "rates": {
//    "AGLD": "2.3263929277654998",
//    "FJD": "2.21592",
//    "MXN": "18.670707655673546",
//    "LVL": "0.651918",
//    "SCR": "13.21713243157135",
//    "CDF": "2068.490771",
//    "BBD": "2.0",
//    "HNL": "24.57644632001569",
//    .
//    .
//    .
//}
//}