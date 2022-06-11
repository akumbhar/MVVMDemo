package com.example.paypay.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paypay.repository.retrofit.Fact

@Database(entities = [Fact::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun localDao(): LocalDao
    companion object {
        private val DATABASE_NAME = "factsDB"
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, AppDatabase.DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}