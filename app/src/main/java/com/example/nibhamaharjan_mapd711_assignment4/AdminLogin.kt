package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.nibhamaharjan_mapd711_assignment4.DAO.AdminDao
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminLogin : AppCompatActivity() {

    private lateinit var database: PizzaDatabase
    private lateinit var adminDao: AdminDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        database = PizzaDatabase.getDatabase(this)
        adminDao = database.adminDao()

        val buttonLogin = findViewById<Button>(R.id.button2)
        buttonLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextText3).text.toString()
            val password = findViewById<EditText>(R.id.editTextText4).text.toString()

            // Validate admin credentials
            lifecycleScope.launch(Dispatchers.IO) {
                val admin = adminDao.getAdmin(username,password)
                if (admin != null && admin.password == password) {
                    // Successful login
                    runOnUiThread {
                        Toast.makeText(this@AdminLogin, "Admin Login successful!", Toast.LENGTH_SHORT).show()
                        saveAdminUsernameToSharedPreferences(username)
                        startActivity(Intent(this@AdminLogin, AdminHomePage::class.java))
                        finish()
                    }
                } else {
                    // Invalid credentials or admin doesn't exist
                    runOnUiThread {
                        Toast.makeText(this@AdminLogin, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val admin_regis= findViewById(R.id.textView8) as TextView
        admin_regis.setOnClickListener {
            startActivity(Intent(this, AdminRegister::class.java))
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
    private fun saveAdminUsernameToSharedPreferences(username: String) {
        val sharedPreferences = getSharedPreferences("AdminPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("admin_username", username)
        editor.apply()
    }
}