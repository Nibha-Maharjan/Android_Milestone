package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nibhamaharjan_mapd711_assignment4.DAO.OrderDao
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import com.example.nibhamaharjan_mapd711_assignment4.model.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminUpdateOrder : AppCompatActivity() {
    //db init
    private lateinit var database: PizzaDatabase
    private lateinit var orderDao: OrderDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_update_order)
        //db define
        database = PizzaDatabase.getDatabase(this)
        orderDao = database.orderDao()

//        val empId = sharedPrefs.getLong("employeeId", 0L)

        // get order and display them in recycle view
        displayOrders()
        }
    //Update order status and emp id
    private fun updateOrderStatus(order: Order) {
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val storedEmployeeId = sharedPreferences.getLong("employeeId", 0L)
        val newStatus = "On its way"
        order.status = newStatus
        order.employeeId = storedEmployeeId


        GlobalScope.launch(Dispatchers.IO) {
            orderDao.updateOrder(order) // Update the order in room db
            runOnUiThread {
                Toast.makeText(this@AdminUpdateOrder, "Order status updated $storedEmployeeId", Toast.LENGTH_SHORT).show()

                displayOrders() // Refresh
            }
        }
    }
    //code for display and fetch order
    private fun displayOrders() {
        val recyclerView: RecyclerView = findViewById(R.id.ordersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.IO) {
            val orders = orderDao.getAllOrders()
            runOnUiThread {
                val adapter = UpdateAdapter(orders) { order -> updateOrderStatus(order) }
                recyclerView.adapter = adapter
            }
        }
    }

}