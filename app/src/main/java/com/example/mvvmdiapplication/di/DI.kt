package com.example.mvvmdiapplication.di

import androidx.room.Room
import com.example.mvvmdiapplication.repository.MainRepository
import com.example.mvvmdiapplication.repository.retrofit.FactsAPI
import com.example.mvvmdiapplication.repository.retrofit.RemoteDao
import com.example.mvvmdiapplication.repository.room.AppDatabase
import com.example.mvvmdiapplication.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { MainViewModel(get()) }
    single { MainRepository(get(), get()) }
    single { RemoteDao(get()) }

    single {
        val API_URL: String = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        retrofit.create(FactsAPI::class.java)
    }


    single {
        val DATABASE_NAME = "factsDB"
        Room.databaseBuilder(
            androidContext()
            , AppDatabase::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
    single { get<AppDatabase>().localDao() }


}