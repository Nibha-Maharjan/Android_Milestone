package com.example.nibhamaharjan_mapd711_assignment4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val employeeId: Long = 0L,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
