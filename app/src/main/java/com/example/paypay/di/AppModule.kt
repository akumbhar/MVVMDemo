package com.example.paypay.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.paypay.CurrencyConverterApplication
import com.example.paypay.repository.retrofit.CurrencyAPI
import com.example.paypay.repository.room.AppDatabase
import com.example.paypay.repository.room.DATABASE_NAME
import com.example.paypay.repository.room.LocalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val API_URL: String = "https://openexchangerates.org/api/";
const val API_KEY: String = "169aa8bc9b9240e39937fad571042dd3";
const val SHARED_PREFS_FILE: String = "myAppSharedPrefs";

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(): CurrencyAPI {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY;
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        return retrofit.create(CurrencyAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideDao(context: Context): LocalDao {
        var dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, DATABASE_NAME
                ).allowMainThreadQueries().build()


        return dbInstance!!.localDao()
    }


    @Singleton
    @Provides
    fun provideApplication(): Context {
        return CurrencyConverterApplication.instance.applicationContext
    }


    @Singleton
    @Provides
    fun providePreferenceHelper(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
    }

}

