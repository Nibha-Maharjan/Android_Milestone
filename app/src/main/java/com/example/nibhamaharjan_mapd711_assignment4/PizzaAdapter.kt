package com.example.nibhamaharjan_mapd711_assignment4


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nibhamaharjan_mapd711_assignment4.model.Pizza

class PizzaAdapter(
    private val pizzas: List<Pizza>,
    private val onOrderClicked: (Pizza) -> Unit // Callback to handle order click
) : RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizza_item, parent, false)
        return PizzaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        val pizza = pizzas[position]
        holder.bind(pizza)
    }

    override fun getItemCount(): Int {
        return pizzas.size
    }

    inner class PizzaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pizzaName: TextView = itemView.findViewById(R.id.pizzaNameTextView)
        private val pizzaPrice: TextView = itemView.findViewById(R.id.pizzaPriceTextView)
        private val orderButton: Button = itemView.findViewById(R.id.orderButton)

        fun bind(pizza: Pizza) {
            pizzaName.text = pizza.pizzaName
            pizzaPrice.text = "$${pizza.price}"

            orderButton.setOnClickListener {
                // Invoke the callback when the order button is clicked
                onOrderClicked(pizza)
            }
        }
    }
}

