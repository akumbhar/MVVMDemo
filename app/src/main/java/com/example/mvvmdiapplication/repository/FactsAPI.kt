package com.example.mvvmdiapplication.repository

import retrofit2.Call
import retrofit2.http.GET

interface FactsAPI {

    @GET("facts.json")
    fun getFacts(): Call<APIResponse>
}