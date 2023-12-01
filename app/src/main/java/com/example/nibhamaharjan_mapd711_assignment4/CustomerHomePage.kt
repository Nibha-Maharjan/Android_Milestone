package com.example.nibhamaharjan_mapd711_assignment4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase

class CustomerHomePage : AppCompatActivity() {
    private lateinit var database: PizzaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_home_page)
    }
}
