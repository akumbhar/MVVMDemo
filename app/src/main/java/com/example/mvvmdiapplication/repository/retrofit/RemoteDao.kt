package com.example.mvvmdiapplication.repository.retrofit

class RemoteDao(private val service: FactsAPI) {

    suspend fun getFactsFromAPI(): APIResponse {
        return service.getFacts()
    }
}