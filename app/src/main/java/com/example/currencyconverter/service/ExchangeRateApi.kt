package com.example.currencyconverter.service

import com.example.currencyconverter.model.api.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Timestamp

interface ExchangeRateApi {
    @GET("latest")
    suspend fun getExchangeRates(@Query("access_key") accessKey: String): Response<ExchangeRateResponse>
}
