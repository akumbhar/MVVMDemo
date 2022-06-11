package com.example.paypay.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paypay.repository.retrofit.Fact

@Dao
interface LocalDao {

    @Query("SELECT * FROM facts")
    fun getAllFacts(): LiveData<List<Fact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(factList: List<Fact>)

    @Query("DELETE FROM facts")
    fun deleteAllFacts()

}