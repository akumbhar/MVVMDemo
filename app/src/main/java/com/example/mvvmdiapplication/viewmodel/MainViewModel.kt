package com.example.mvvmdiapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdiapplication.common.doLogE
import com.example.mvvmdiapplication.repository.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val apiResponseMutableLiveData:MutableLiveData<String> = MutableLiveData()
    val apiResponseLiveData: LiveData<String> = apiResponseMutableLiveData

    fun getFacts(){
        repository.getFacts { response, error ->

            response?.let {
                doLogE(it.toString())
                apiResponseMutableLiveData.postValue(it.toString())

            }

            error?.let {
                doLogE(it)
                apiResponseMutableLiveData.postValue(it)

            }

        }
    }
}