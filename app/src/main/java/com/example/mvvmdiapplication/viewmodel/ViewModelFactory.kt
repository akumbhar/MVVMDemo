package com.example.mvvmdiapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdiapplication.repository.MainRepository
import com.example.mvvmdiapplication.repository.retrofit.RemoteDao
import com.example.mvvmdiapplication.repository.room.AppDatabase

class ViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        when(modelClass){
            //MainViewModel::class.java -> return MainViewModel(MainRepository(RemoteDao(),AppDatabase.getInstance(context)!!.localDao())) as T
            MainViewModel::class.java -> return MainViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class");
        }
    }
}