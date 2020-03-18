package com.example.mvvmdiapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmdiapplication.MainRepository

class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        when(modelClass){
            MainViewModel::class.java -> return MainViewModel(MainRepository()) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class");
        }
    }
}