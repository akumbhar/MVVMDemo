package com.example.mvvmdiapplication.repository

import io.reactivex.Observable
import retrofit2.http.GET

interface FactsAPI {

    @GET("facts.json")
    fun getFacts(): Observable<APIResponse>
}