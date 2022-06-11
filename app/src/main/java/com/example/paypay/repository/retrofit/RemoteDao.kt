package com.example.paypay.repository.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDao {

    private val apiUrl: String = "https://openexchangerates.org/api/";
    private val apiKey: String = "169aa8bc9b9240e39937fad571042dd3";
    private var service: CurrencyAPI

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY;
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        service = retrofit.create(CurrencyAPI::class.java)
    }

    suspend fun getCurrencies() = service.getCurrencies(apiKey)
    suspend fun getCurrencyConversions() = service.getConversions(apiKey)
}