package com.example.currencyconverter.model.api
import java.sql.Timestamp

data class ExchangeRateResponse(
    val success: Boolean,
    val timestamp: String,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)