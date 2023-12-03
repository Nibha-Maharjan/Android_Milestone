    package com.example.nibhamaharjan_mapd711_assignment4

    import android.content.Context
    import android.content.SharedPreferences
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.ArrayAdapter
    import android.widget.Button
    import android.widget.Spinner
    import android.widget.TextView
    import android.widget.Toast
    import com.example.nibhamaharjan_mapd711_assignment4.DAO.OrderDao
    import com.example.nibhamaharjan_mapd711_assignment4.db.PizzaDatabase
    import com.example.nibhamaharjan_mapd711_assignment4.model.Order
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.GlobalScope
    import kotlinx.coroutines.launch
    import java.text.SimpleDateFormat
    import java.util.Calendar
    import java.util.Date
    import java.util.Locale

    class OrderScreen : AppCompatActivity() {
        private lateinit var sharedPrefs: SharedPreferences
        private lateinit var usrName : TextView
        private lateinit var pizzName : TextView
        private lateinit var pizzPrice : TextView
        private lateinit var pizzCat : TextView
        private lateinit var orderDate : TextView
        private lateinit var orderDao: OrderDao


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_order_screen)
            //init order dao
            orderDao = PizzaDatabase.getDatabase(this).orderDao()

            sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val userName = sharedPrefs.getString("username", null)
            val PizzaName = intent.getStringExtra("pizzaName")
            val PizzaPrice = intent.getStringExtra("pizzaPrice")
            val PizzaCategory = intent.getStringExtra("pizzaCategory")

            usrName=findViewById(R.id.textView)
            pizzName=findViewById(R.id.textView29)
            pizzPrice=findViewById(R.id.textView30)
            pizzCat=findViewById(R.id.textView31)
            orderDate=findViewById(R.id.textView32)

            val calendar = Calendar.getInstance()
            val orderDateText = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"
            val pizzaId = intent.getStringExtra("pizzaId")
            val cusID = sharedPrefs.getLong("customerId", 0L)


            usrName.text = "Customer Name : $userName"
            pizzName.text = "Pizza : "+PizzaName
            pizzPrice.text = "Price : $$PizzaPrice"
            pizzCat.text = "Category : "+ PizzaCategory
//            pizzName.text = "Pizza ID: $pizzaId"
//            pizzPrice.text="cus id: $cusID"
            orderDate.text = "Order Date: $orderDateText"

            val spinner: Spinner = findViewById(R.id.spinner)
            // Create an ArrayAdapter using the string array and a default spinner layout.
            ArrayAdapter.createFromResource(
                this,
                R.array.quantity,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears.
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner.
                spinner.adapter = adapter
            }
            val button = findViewById<Button>(R.id.button6)
            button.setOnClickListener {
                val pizzaId = intent.getStringExtra("pizzaId") ?: ""
                val quantitySpinner = findViewById<Spinner>(R.id.spinner)
                val quantity = quantitySpinner.selectedItem.toString()
                val cusID = sharedPrefs.getLong("customerId", 0L)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())


                val order = Order(
                    customerId = cusID,
                    productId = pizzaId.toLongOrNull() ?: 0L,
                    employeeId = -1L, // Set employeeId to 0 for now
                    orderDate = dateFormat.format(Date())
                        .toString(), // You can use your desired date logic here
                    quantity = quantity.toIntOrNull() ?: 1,
                    status = "Pending"
                )

                GlobalScope.launch(Dispatchers.IO) {
                    orderDao.insertOrder(order)
                    runOnUiThread {
                        Toast.makeText(
                            this@OrderScreen,
                            "Order placed successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }

            }

        }

    }