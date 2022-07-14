package com.example.assignment.repository

import com.example.assignment.repository.retrofit.Currency
import com.example.assignment.repository.retrofit.RemoteDao
import com.example.assignment.repository.room.LocalDao
import javax.inject.Inject


class MainRepository  @Inject constructor(private val remoteDao: RemoteDao, private val localDao: LocalDao) {
    fun deleteAllCurrencies() = localDao.deleteAllCurrencies()
    fun insertAllCurrencies(currencyList: List<Currency>) = localDao.insertAll(currencyList)
    fun getAllCurrencies() = localDao.getAllCurrencies()
    fun getCurrencyById(selectedCurrency: String) = localDao.getCurrencyById(selectedCurrency)
    suspend fun getSongs()=remoteDao.getSongs()
}