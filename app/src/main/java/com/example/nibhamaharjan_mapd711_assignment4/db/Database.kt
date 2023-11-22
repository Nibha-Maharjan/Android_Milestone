package com.example.nibhamaharjan_mapd711_assignment4.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nibhamaharjan_mapd711_assignment4.DAO.AdminDao
import com.example.nibhamaharjan_mapd711_assignment4.DAO.CustomerDao
import com.example.nibhamaharjan_mapd711_assignment4.DAO.OrderDao
import com.example.nibhamaharjan_mapd711_assignment4.DAO.PizzaDao
import com.example.nibhamaharjan_mapd711_assignment4.model.Customer
import com.example.nibhamaharjan_mapd711_assignment4.model.Admin
import com.example.nibhamaharjan_mapd711_assignment4.model.Pizza
import com.example.nibhamaharjan_mapd711_assignment4.model.Order

@Database(entities = [Customer::class, Admin::class, Pizza::class, Order::class], version = 1)
abstract class PizzaDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun adminDao(): AdminDao
    abstract fun pizzaDao(): PizzaDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: PizzaDatabase? = null

        fun getDatabase(context: Context): PizzaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PizzaDatabase::class.java,
                    "pizza_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
