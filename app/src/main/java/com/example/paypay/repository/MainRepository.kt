package com.example.paypay.repository

import com.example.paypay.repository.retrofit.Currency
import com.example.paypay.repository.retrofit.RemoteDao
import com.example.paypay.repository.room.LocalDao
import javax.inject.Inject


class MainRepository  @Inject constructor(private val remoteDao: RemoteDao, private val localDao: LocalDao) {
    suspend fun getCurrencies() = remoteDao.getCurrencies()
    suspend fun getConversions() = remoteDao.getCurrencyConversions()
    fun deleteAllCurrencies() = localDao.deleteAllCurrencies()
    fun insertAllCurrencies(currencyList: List<Currency>) = localDao.insertAll(currencyList)
    fun getAllCurrencies() = localDao.getAllCurrencies()
    fun getCurrencyById(selectedCurrency: String) = localDao.getCurrencyById(selectedCurrency)
}