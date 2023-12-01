package com.example.nibhamaharjan_mapd711_assignment4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import com.example.nibhamaharjan_mapd711_assignment4.model.Pizza
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminHomePage : AppCompatActivity() {

    private lateinit var database: PizzaDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home_page)
        database = PizzaDatabase.getDatabase(this)

        val buttonAdd = findViewById<Button>(R.id.button5)
        buttonAdd.setOnClickListener {
            val pizzaName = findViewById<EditText>(R.id.editTextText9).text.toString()
            val price = findViewById<EditText>(R.id.editTextText10).text.toString().toDouble()
            val category = findViewById<EditText>(R.id.editTextText11).text.toString()

            if (pizzaName.isNotBlank() && price != null && category.isNotBlank()) {
                val newPizza = Pizza(pizzaName = pizzaName, price = price, category = category)

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

}