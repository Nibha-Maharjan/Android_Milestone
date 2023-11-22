package com.example.nibhamaharjan_mapd711_assignment4.DAO

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nibhamaharjan_mapd711_assignment4.model.Customer

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer WHERE userName = :userName AND password = :password")
    suspend fun getCustomer(userName: String, password: String): Customer?

    @Insert
    suspend fun insertCustomer(customer: Customer)


    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)
    // Other necessary queries for customer operations
}
