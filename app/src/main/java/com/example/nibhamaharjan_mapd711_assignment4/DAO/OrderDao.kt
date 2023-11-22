package com.example.nibhamaharjan_mapd711_assignment4.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.nibhamaharjan_mapd711_assignment4.model.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM `order` WHERE customerId = :customerId")
    suspend fun getOrdersForCustomer(customerId: Long): List<Order>

    @Query("UPDATE `order` SET status = :status WHERE orderId = :orderId")
    suspend fun updateOrderStatus(orderId: Long, status: String)

    @Insert
    suspend fun insertOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)
    // Other necessary queries for order operations
}
