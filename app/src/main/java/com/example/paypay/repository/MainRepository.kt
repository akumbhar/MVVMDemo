package com.example.paypay.repository

import com.example.paypay.repository.retrofit.Currency
import com.example.paypay.repository.retrofit.RemoteDao
import com.example.paypay.repository.room.LocalDao


class MainRepository(private val remoteDao: RemoteDao, val localDao: LocalDao) {

    suspend fun getCurrencies() = remoteDao.getCurrencies()
    suspend fun getConversions() = remoteDao.getCurrencyConversions()

    fun deleteAllCurrencies() = localDao.deleteAllCurrencies()
    fun insertAllCurrencies(currencyList: List<Currency>) = localDao.insertAll(currencyList)
    fun getAllCurrencies() = localDao.getAllCurrencies()

}