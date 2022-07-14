package com.example.assignment.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.repository.retrofit.Currency

const val DATABASE_NAME = "currencyDB"

@Database(entities = [Currency::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao
}