package com.example.paypay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paypay.repository.retrofit.Fact
import com.example.paypay.repository.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val apiResponseMutableLiveData: MutableLiveData<List<Fact>> = MutableLiveData()
    var apiResponseLiveData: LiveData<List<Fact>> = apiResponseMutableLiveData

    fun getFacts() {
        apiResponseLiveData = repository.getFacts()

    }
}