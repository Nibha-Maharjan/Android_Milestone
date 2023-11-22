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
import com.example.nibhamaharjan_mapd711_assignment4.model.Admin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminRegister : AppCompatActivity() {
    private lateinit var database: PizzaDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_register)


        database = PizzaDatabase.getDatabase(this)

        val buttonRegister = findViewById<Button>(R.id.button4)
        buttonRegister.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextText5).text.toString()
            val password = findViewById<EditText>(R.id.editTextText6).text.toString()
            val firstName = findViewById<EditText>(R.id.editTextText7).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextText8).text.toString()

            val newAdmin = Admin(
                username = username,
                password = password,
                firstName = firstName,
                lastName = lastName
            )

            insertAdmin(newAdmin)
        }
        val admin_log= findViewById(R.id.textView20) as TextView

        admin_log.setOnClickListener {
            startActivity(Intent(this, AdminLogin::class.java))
        }
    }
    private fun insertAdmin(admin: Admin) {
        lifecycleScope.launch(Dispatchers.IO) {
            database.adminDao().insertAdmin(admin)
        }
        Toast.makeText(this, "Admin registered successfully!", Toast.LENGTH_SHORT).show()
        // Redirect to login or another appropriate screen
        startActivity(Intent(this, AdminLogin::class.java))
        finish() // Finish this activity to prevent going back to registration with back button
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