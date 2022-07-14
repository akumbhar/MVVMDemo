package com.example.assignment.di

import android.content.Context
import androidx.room.Room
import com.example.assignment.SongsApplication
import com.example.assignment.repository.retrofit.CurrencyAPI
import com.example.assignment.repository.room.AppDatabase
import com.example.assignment.repository.room.DATABASE_NAME
import com.example.assignment.repository.room.LocalDao
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


const val API_URL: String =
    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/";


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
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder()
                        .exceptionOnUnreadXml(false)
                        .build()
                )
            )

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
        return SongsApplication.instance.applicationContext
    }
}

