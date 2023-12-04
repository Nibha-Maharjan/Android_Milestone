package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import com.example.nibhamaharjan_mapd711_assignment4.model.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerRegister : AppCompatActivity() {
    //db init
    private lateinit var database: PizzaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register)
        title = "Customer Register"
        //Login screen
        val cus_log= findViewById(R.id.textView10) as TextView
        cus_log.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        //db define
        database = PizzaDatabase.getDatabase(this)
        //setonclick action
        val button3 = findViewById<Button>(R.id.button3)
        button3.setOnClickListener {
            val userName = findViewById<EditText>(R.id.editTextText12).text.toString()
            val password = findViewById<EditText>(R.id.editTextText13).text.toString()
            val firstName = findViewById<EditText>(R.id.editTextText14).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextText15).text.toString()
            val address = findViewById<EditText>(R.id.editTextText17).text.toString()
            val city = findViewById<EditText>(R.id.editTextText18).text.toString()
            val postalCode = findViewById<EditText>(R.id.editTextText19).text.toString()

            val newCustomer = Customer(
                userName = userName,
                password = password,
                firstName = firstName,
                lastName = lastName,
                address = address,
                city = city,
                postalCode = postalCode
            )
            //write newcustomer data in database
            insertCustomer(newCustomer)
        }
    }
    //take customer data and put in db
    private fun insertCustomer(customer: Customer) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.customerDao().insertCustomer(customer)
        }
        Toast.makeText(this, "Customer registered successfully!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.customer_menu -> {
                Toast.makeText(this, "Welcome Customer", Toast.LENGTH_SHORT).show()
                Intent(this, MainActivity::class.java)
            }
            R.id.admin_menu -> {
                Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show()
                Intent(this, AdminLogin::class.java)
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        startActivity(intent)
        return true
    }
}