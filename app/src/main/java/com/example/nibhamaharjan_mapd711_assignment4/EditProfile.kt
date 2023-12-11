package com.example.nibhamaharjan_mapd711_assignment4


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.nibhamaharjan_mapd711_assignment4.DAO.CustomerDao
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import com.example.nibhamaharjan_mapd711_assignment4.model.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.Map

class EditProfile : AppCompatActivity() {

    //database init
    private lateinit var database: PizzaDatabase
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        title = "Edit Profile"
        //database and sharedpref define
        database = PizzaDatabase.getDatabase(this)
        sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        //update button setonclick action
        val updateButton = findViewById<Button>(R.id.button3cus)
        updateButton.setOnClickListener {
            val userNameEditText = findViewById<EditText>(R.id.editTextText12cus)
            val passwordEditText = findViewById<EditText>(R.id.editTextText13cus)
            val firstNameEditText = findViewById<EditText>(R.id.editTextText14cus)
            val lastNameEditText = findViewById<EditText>(R.id.editTextText15cus)
            val addressEditText = findViewById<EditText>(R.id.editTextText17cus)
            val cityEditText = findViewById<EditText>(R.id.editTextText18cus)
            val postalCodeEditText = findViewById<EditText>(R.id.editTextText19cus)
            val cusID = sharedPrefs.getLong("customerId", 0L)

            val updatedUserName = userNameEditText.text.toString()
            val updatedPassword = passwordEditText.text.toString()
            val updatedFirstName = firstNameEditText.text.toString()
            val updatedLastName = lastNameEditText.text.toString()
            val updatedAddress = addressEditText.text.toString()
            val updatedCity = cityEditText.text.toString()
            val updatedPostalCode = postalCodeEditText.text.toString()

            //Checking if data is being passed
//            Log.d("UserProfileUpdate", "Updated Username: $updatedUserName")
//            Log.d("UserProfileUpdate", "Updated Password: $updatedPassword")
//            Log.d("UserProfileUpdate", "Updated First Name: $updatedFirstName")
//            Log.d("UserProfileUpdate", "Updated Last Name: $updatedLastName")
//            Log.d("UserProfileUpdate", "Updated Address: $updatedAddress")
//            Log.d("UserProfileUpdate", "Updated City: $updatedCity")
//            Log.d("UserProfileUpdate", "Updated Postal Code: $updatedPostalCode")


            val updatedCustomer = Customer(
                customerId= cusID,
                userName = updatedUserName,
                password = updatedPassword,
                firstName = updatedFirstName,
                lastName = updatedLastName,
                address = updatedAddress,
                city = updatedCity,
                postalCode = updatedPostalCode
            )
            //Updating customer table
            insertCustomer(updatedCustomer)
        }
    }

    //writing the data
    private fun insertCustomer(customer: Customer) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.customerDao().updateCustomer(customer)
        }
        Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show()
        // Redirect to login or another appropriate screen
        startActivity(Intent(this, CustomerHomePage::class.java))
        finish()
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