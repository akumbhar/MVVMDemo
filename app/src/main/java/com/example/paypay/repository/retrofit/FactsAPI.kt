package com.example.paypay.repository.retrofit

import com.example.paypay.repository.retrofit.APIResponse
import retrofit2.Call
import retrofit2.http.GET

interface FactsAPI {

    @GET("facts.json")
    fun getFacts(): Call<APIResponse>
}