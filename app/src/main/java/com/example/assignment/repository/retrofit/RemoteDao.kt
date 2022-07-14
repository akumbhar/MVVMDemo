package com.example.assignment.repository.retrofit

import javax.inject.Inject

class RemoteDao @Inject constructor(private val service: CurrencyAPI) {

    suspend fun getSongs() = service.getXmlConversions()
}