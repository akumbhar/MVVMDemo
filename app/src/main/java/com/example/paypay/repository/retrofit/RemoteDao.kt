package com.example.paypay.repository.retrofit

import com.example.paypay.di.API_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDao @Inject constructor(private val service: CurrencyAPI) {

    suspend fun getCurrencies() = service.getCurrencies(API_KEY)
    suspend fun getCurrencyConversions() = service.getConversions(API_KEY)
}