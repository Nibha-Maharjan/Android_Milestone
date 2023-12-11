package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nibhamaharjan_mapd711_assignment4.DAO.PizzaDao
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import com.example.nibhamaharjan_mapd711_assignment4.model.Pizza
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomerHomePage : AppCompatActivity() {
    //init sharedpref and db
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var database: PizzaDatabase
    private lateinit var pizzaDao: PizzaDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home_page)
        title = "Customer Homepage"
        //define db
        database = PizzaDatabase.getDatabase(this)
        pizzaDao = database.pizzaDao()

        // fetch pizza and show in recycler view
        displayPizzas()
    }
    //order button click intent send data to another screen
    private fun orderPizza(pizza: Pizza) {
        val intent = Intent(this@CustomerHomePage, OrderScreen::class.java)
        intent.putExtra("pizzaId", pizza.pizzaId.toString())
        intent.putExtra("pizzaName", pizza.pizzaName)
        intent.putExtra("pizzaPrice", pizza.price.toString())
        intent.putExtra("pizzaCategory", pizza.category)
        startActivity(intent)

        Toast.makeText(this@CustomerHomePage, "Ordered: ${pizza.pizzaName}", Toast.LENGTH_SHORT).show()

    }
    //display pizza
    private fun displayPizzas() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.IO) {
            val pizzas = pizzaDao.getAllPizzas()
            runOnUiThread {
                val adapter = PizzaAdapter(pizzas) { pizza -> orderPizza(pizza) }
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
            R.id.logout_cus -> {
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
                Intent(this, MainActivity::class.java)
            }
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
            R.id.map -> {
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show()
                Intent(this, Map::class.java)
            }
            R.id.topshop -> {
                Toast.makeText(this, "WebView", Toast.LENGTH_SHORT).show()
                Intent(this, TopPizzas::class.java)
            }


            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        startActivity(intent)
        return true
    }
}
