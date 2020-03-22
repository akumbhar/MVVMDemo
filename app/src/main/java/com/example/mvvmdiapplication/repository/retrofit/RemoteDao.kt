package com.example.mvvmdiapplication.repository.retrofit

import com.example.mvvmdiapplication.repository.APIResponse
import com.example.mvvmdiapplication.repository.FactsAPI
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDao {

    val API_URL: String = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    private var service: FactsAPI
    val TAG = RemoteDao::class.java.simpleName

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        service = retrofit.create(FactsAPI::class.java)
    }

    fun getFactsFromAPI(): Observable<APIResponse> {

        return service.getFacts()
    }
}