package com.example.mvvmdiapplication.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmdiapplication.repository.Fact

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