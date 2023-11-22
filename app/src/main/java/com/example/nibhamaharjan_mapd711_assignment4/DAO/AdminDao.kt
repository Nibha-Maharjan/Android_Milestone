package com.example.nibhamaharjan_mapd711_assignment4.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nibhamaharjan_mapd711_assignment4.model.Admin

@Dao
interface AdminDao {
    @Query("SELECT * FROM admin WHERE username = :username AND password = :password")
    suspend fun getAdmin(username: String, password: String): Admin?

    @Insert
    suspend fun insertAdmin(admin: Admin)

    @Update
    suspend fun updateAdmin(admin: Admin)

    @Delete
    suspend fun deleteAdmin(admin: Admin)
    // Other necessary queries for admin operations
}
