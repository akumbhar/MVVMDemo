package com.example.paypay.repository

import android.content.SharedPreferences
import com.example.paypay.repository.retrofit.Currency
import com.example.paypay.repository.retrofit.RemoteDao
import com.example.paypay.repository.room.LocalDao
import javax.inject.Inject


class MainRepository  @Inject constructor(private val remoteDao: RemoteDao, private val localDao: LocalDao, private val sharedPrefDao : SharedPreferences) {
    suspend fun getCurrencies() = remoteDao.getCurrencies()
    suspend fun getConversions() = remoteDao.getCurrencyConversions()
    fun deleteAllCurrencies() = localDao.deleteAllCurrencies()
    fun insertAllCurrencies(currencyList: List<Currency>) = localDao.insertAll(currencyList)
    fun getAllCurrencies() = localDao.getAllCurrencies()
    fun getCurrencyById(selectedCurrency: String) = localDao.getCurrencyById(selectedCurrency)

    fun insertToSharedPrefs(user: String) {
        sharedPrefDao.edit().putString("SomeKey", user).apply()
    }
}