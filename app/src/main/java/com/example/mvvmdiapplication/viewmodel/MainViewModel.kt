package com.example.mvvmdiapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdiapplication.repository.MainRepository
import com.example.mvvmdiapplication.repository.retrofit.Fact
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val apiResponseMutableLiveData: MutableLiveData<List<Fact>> = MutableLiveData()
    var apiResponseLiveData: LiveData<List<Fact>> = apiResponseMutableLiveData

    fun getFacts() {
        GlobalScope.launch {
            try {
                apiResponseMutableLiveData.postValue(repository.getFacts())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}