package com.example.nibhamaharjan_mapd711_assignment4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pizza")
data class Pizza(
    @PrimaryKey(autoGenerate = true)
    val pizzaId: Long = 0L,
    val pizzaName: String,
    val price: Double,
    val category: String
)
