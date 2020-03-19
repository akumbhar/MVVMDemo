package com.example.mvvmdiapplication.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class APIResponse(
    val rows: List<Fact>,
    val title: String
)

@Entity(tableName = "facts")
data class Fact(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "url")
    val imageHref: String?
)