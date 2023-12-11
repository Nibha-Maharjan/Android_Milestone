package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import kotlin.collections.Map

class TopPizzas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_pizzas)
        title = "Web View"

        val wv = findViewById<View>(R.id.web_content) as WebView
        //enabling Javascript
        wv.settings.javaScriptEnabled

        //loading the URL
        wv.loadUrl("https://www.tastetoronto.com/guides/best-pizza-toronto")
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