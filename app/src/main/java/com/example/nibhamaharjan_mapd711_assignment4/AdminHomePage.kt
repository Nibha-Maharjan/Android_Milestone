package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import com.example.nibhamaharjan_mapd711_assignment4.model.Pizza
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminHomePage : AppCompatActivity() {
    //database init
    private lateinit var database: PizzaDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_page)
        //define database
        database = PizzaDatabase.getDatabase(this)
        //button onclick action
        val buttonAdd = findViewById<Button>(R.id.button5)
        buttonAdd.setOnClickListener {
            val pizzaName = findViewById<EditText>(R.id.editTextText9).text.toString()
            val price = findViewById<EditText>(R.id.editTextText10).text.toString().toDouble()
            val category = findViewById<EditText>(R.id.editTextText11).text.toString()

            if (pizzaName.isNotBlank() && price != null && category.isNotBlank()) {
                val newPizza = Pizza(pizzaName = pizzaName, price = price, category = category)
                //add new pizza into database and clear on success
                insertPizza(newPizza)
                findViewById<EditText>(R.id.editTextText9).text.clear()
                findViewById<EditText>(R.id.editTextText10).text.clear()
                findViewById<EditText>(R.id.editTextText11).text.clear()
                Toast.makeText(this, "Pizza added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertPizza(pizza: Pizza) {
        GlobalScope.launch(Dispatchers.IO) {
            database.pizzaDao().insertPizza(pizza)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
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