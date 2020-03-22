package com.example.mvvmdiapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdiapplication.repository.Fact
import com.example.mvvmdiapplication.repository.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val apiResponseMutableLiveData: MutableLiveData<List<Fact>> = MutableLiveData()
    var apiResponseLiveData: LiveData<List<Fact>> = apiResponseMutableLiveData

    fun getFacts() {
        apiResponseLiveData = repository.getFacts()

    }
}