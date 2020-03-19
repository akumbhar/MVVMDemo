package com.example.mvvmdiapplication.repository

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


typealias GetFactsCallback = (APIResponse?, String?) -> Unit

class MainRepository(val remoteDao: RemoteDao) {

    val TAG = MainRepository::class.java.simpleName

    fun getFacts(callback: GetFactsCallback) {

        val retrofitCallback = object : Callback<APIResponse> {

            override fun onFailure(call: Call<APIResponse>?, t: Throwable?) {
                callback.invoke(null, t!!.localizedMessage)
            }

            override fun onResponse(call: Call<APIResponse>?, r: Response<APIResponse>?) {
                callback.invoke(r!!.body(), null)
            }

        }
        remoteDao.getFactsFromAPI(retrofitCallback)


    }
}