package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
        title = "Update Order"
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.logout_adm -> {
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
                Intent(this, AdminLogin::class.java)
            }
            R.id.admin_home -> {
                Toast.makeText(this, "Add new Pizza", Toast.LENGTH_SHORT).show()
                Intent(this, AdminHomePage::class.java)
            }
            R.id.update_order -> {
                Toast.makeText(this, "Update Order", Toast.LENGTH_SHORT).show()
                Intent(this, AdminUpdateOrder::class.java)
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        startActivity(intent)
        return true
    }

}