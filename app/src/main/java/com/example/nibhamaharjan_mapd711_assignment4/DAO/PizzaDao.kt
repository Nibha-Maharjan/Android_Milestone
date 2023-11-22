package com.example.nibhamaharjan_mapd711_assignment4.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nibhamaharjan_mapd711_assignment4.model.Pizza

@Dao
interface PizzaDao {
    @Query("SELECT * FROM pizza")
    suspend fun getAllPizzas(): List<Pizza>

    @Query("SELECT * FROM pizza WHERE category = :category")
    suspend fun getPizzasByCategory(category: String): List<Pizza>

    @Insert
    suspend fun insertPizza(pizza: Pizza)

    @Update
    suspend fun updatePizza(pizza: Pizza)

    @Delete
    suspend fun deletePizza(pizza: Pizza)
    // Other necessary queries for pizza operations
}
