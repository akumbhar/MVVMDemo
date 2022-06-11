package com.example.paypay.repository.retrofit

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("currencies.json")
    suspend fun getCurrencies(@Query("app_id") id: String): Response<JsonObject>

    @GET("latest.json")
    suspend fun getConversions(@Query("app_id") id: String): Response<ConversionResponse>
}