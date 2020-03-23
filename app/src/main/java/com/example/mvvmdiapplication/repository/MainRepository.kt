package com.example.mvvmdiapplication.repository

import com.example.mvvmdiapplication.repository.retrofit.Fact
import com.example.mvvmdiapplication.repository.retrofit.RemoteDao
import com.example.mvvmdiapplication.repository.room.LocalDao


class MainRepository(private val remoteDao: RemoteDao, private val localDao: LocalDao) {

    suspend fun getFacts(): List<Fact> {
        val factResponse = remoteDao.getFactsFromAPI()
        localDao.deleteAllFacts()
        localDao.insertAll(factResponse.rows)
        return localDao.getAllFacts()
    }
}