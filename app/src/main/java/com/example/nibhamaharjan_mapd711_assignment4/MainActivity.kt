// Name: Nibha Maharjan
// Student ID: 301282952
// Name: Saurav Gautam
// Student ID: 301286980
// Date Completed: Dec 3rd 2023
// App Desc: Pizza for 4th assignment
// This App stores multiple data in Android room database and performs crud operations
package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Context
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
import com.example.nibhamaharjan_mapd711_assignment4.DAO.CustomerDao
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //Database init
    private lateinit var database: PizzaDatabase
    private lateinit var customerDao: CustomerDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Customer Login"
        //Defining database
        database = PizzaDatabase.getDatabase(this)
        customerDao = database.customerDao()
        //Button onclick action
        val buttonLogin = findViewById<Button>(R.id.button)
        buttonLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextText).text.toString()
            val password = findViewById<EditText>(R.id.editTextText2).text.toString()


            // check user's credential in DB
            lifecycleScope.launch(Dispatchers.IO) {
                val customer = customerDao.getCustomer(username, password)
                if (customer != null) {
                    // Success
                    runOnUiThread {
                        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.putLong("customerId", customer.customerId)
                        editor.apply()
                        startActivity(Intent(this@MainActivity, CustomerHomePage::class.java))
                        Toast.makeText(this@MainActivity, "Login successful! $username", Toast.LENGTH_SHORT).show()

                        finish()
                    }
                } else {
                    // Fail
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        //Change to registration screen
        val cus_regis= findViewById(R.id.textView5) as TextView
        cus_regis.setOnClickListener {
            startActivity(Intent(this, CustomerRegister::class.java))
        }
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
    private fun saveUsernameToSharedPreferences(username: String) {

    }
}