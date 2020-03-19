package com.example.mvvmdiapplication.repository

import androidx.lifecycle.LiveData
import com.example.mvvmdiapplication.repository.room.LocalDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainRepository(val remoteDao: RemoteDao, val localDao: LocalDao) {

    fun getFacts(): LiveData<List<Fact>> {

        val retrofitCallback = object : Callback<APIResponse> {

            override fun onFailure(call: Call<APIResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<APIResponse>?, r: Response<APIResponse>?) {
                r?.body()?.let {
                    localDao.deleteAllFacts()
                    localDao.insertAll(it.rows)
                }
            }

        }
        remoteDao.getFactsFromAPI(retrofitCallback)
        return localDao.getAllFacts()
    }
}