package com.example.currencyconverter.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.exchangeratesapi.io/v1/"

    val api: ExchangeRateApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ExchangeRateApi::class.java)
}

