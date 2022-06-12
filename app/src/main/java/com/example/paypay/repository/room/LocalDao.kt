package com.example.paypay.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paypay.repository.retrofit.Currency

@Dao
interface LocalDao {

    /* @Query("SELECT * FROM currency")
     fun getAllCurrencies(): LiveData<List<Currency>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencyList: List<Currency>)

    @Query("DELETE FROM currency")
    fun deleteAllCurrencies()

    @Query("SELECT * FROM currency order by currency")
    fun getAllCurrencies(): List<Currency>

    @Query("SELECT * FROM currency WHERE currency=:selectedCurrency")
    fun getCurrencyById(selectedCurrency: String): Currency

}