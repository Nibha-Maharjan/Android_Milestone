package com.example.nibhamaharjan_mapd711_assignment4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nibhamaharjan_mapd711_assignment4.DAO.OrderDao
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewCustomerOrder : AppCompatActivity() {
    private lateinit var orderDao: OrderDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter
    private lateinit var sharedPrefs: SharedPreferences

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_customer_order)
        title = "View Orders"
        orderDao = PizzaDatabase.getDatabase(this).orderDao()
        recyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(this)

        sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val customerId = sharedPrefs.getLong("customerId", 0L)


            GlobalScope.launch(Dispatchers.IO) {
                val orders = orderDao.getOrdersForCustomer(customerId)
                runOnUiThread {
                    adapter = OrderAdapter(orders)
                    recyclerView.adapter = adapter
                }
            }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.customer_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.customer_home -> {
                Toast.makeText(this, "Customer Home", Toast.LENGTH_SHORT).show()
                Intent(this, CustomerHomePage::class.java)
            }
            R.id.edit_profile -> {
                Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show()
                Intent(this, EditProfile::class.java)
            }
            R.id.view_order -> {
                Toast.makeText(this, "View Your Order", Toast.LENGTH_SHORT).show()
                Intent(this, ViewCustomerOrder::class.java)
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        startActivity(intent)
        return true
    }
}