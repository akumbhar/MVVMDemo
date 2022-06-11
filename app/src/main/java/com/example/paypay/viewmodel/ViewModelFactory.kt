package com.example.paypay.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paypay.repository.MainRepository
import com.example.paypay.repository.retrofit.RemoteDao
import com.example.paypay.repository.room.AppDatabase

class ViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        when(modelClass){
            MainViewModel::class.java -> return MainViewModel(MainRepository(RemoteDao(),AppDatabase.getInstance(context)!!.localDao())) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class");
        }
    }
}