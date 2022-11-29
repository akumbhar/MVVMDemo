package com.example.paypay.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paypay.repository.retrofit.Currency

const val DATABASE_NAME = "currencyDB"

@Database(entities = [Currency::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao
}