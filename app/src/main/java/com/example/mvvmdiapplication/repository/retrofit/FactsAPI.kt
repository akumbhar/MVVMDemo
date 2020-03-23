package com.example.mvvmdiapplication.repository.retrofit

import retrofit2.http.GET

interface FactsAPI {

    @GET("facts.json")
    suspend fun getFacts(): APIResponse
}