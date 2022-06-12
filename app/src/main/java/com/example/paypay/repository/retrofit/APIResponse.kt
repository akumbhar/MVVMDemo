package com.example.paypay.repository.retrofit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject


@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    @ColumnInfo(name = "currency")
    val currencyName: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "conversionRate")
    val conversionRate: Double = 0.0
)


data class ConversionResponse(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: JsonObject? = null,
    val timestamp: Int
)
