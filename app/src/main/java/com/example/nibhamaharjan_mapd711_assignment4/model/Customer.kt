package com.example.nibhamaharjan_mapd711_assignment4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val customerId: Long = 0L,
    val userName: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val address: String,
    val city: String,
    val postalCode: String
)
