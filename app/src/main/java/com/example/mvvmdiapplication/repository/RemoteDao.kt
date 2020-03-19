package com.example.mvvmdiapplication.repository

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDao {

    val API_URL: String = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    private var retrofitServie : FactsAPI
    val TAG = RemoteDao::class.java.simpleName

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
         retrofitServie = retrofit.create(FactsAPI::class.java)
    }

    fun getFactsFromAPI(){
        retrofitServie.getFacts().enqueue(
        object : Callback<APIResponse> {

            override fun onFailure(call: Call<APIResponse>?, t: Throwable?)  {
                // handle error
                Log.e(TAG, "onFailure")
            }

            override fun onResponse(call: Call<APIResponse>?, r: Response<APIResponse>?) {
                // handle success
                Log.e(TAG, "onResponse")

            }

        })

    }
}