package com.example.mvvmdiapplication.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmdiapplication.repository.retrofit.Fact

@Database(entities = [Fact::class], version = 1 , exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun localDao(): LocalDao
}