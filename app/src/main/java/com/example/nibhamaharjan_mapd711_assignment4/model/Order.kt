package com.example.nibhamaharjan_mapd711_assignment4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0L,
    val customerId: Long,
    val productId: Long,
    var employeeId: Long,
    val orderDate: String,
    val quantity: Int,
    var status: String
)
