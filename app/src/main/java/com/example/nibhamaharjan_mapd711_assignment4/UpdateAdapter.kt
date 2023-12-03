package com.example.nibhamaharjan_mapd711_assignment4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nibhamaharjan_mapd711_assignment4.model.Order

class UpdateAdapter(
    private val orders: List<Order>,
    private val updateOrderStatusListener: (Order) -> Unit
) : RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.update_order, parent, false)
        return UpdateViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpdateViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
        holder.updateStatusButton.setOnClickListener {
            updateOrderStatusListener(order)
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class UpdateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderIdTextView: TextView = itemView.findViewById(R.id.orderIdTextView)
        private val customerIdTextView: TextView = itemView.findViewById(R.id.customerIdTextView)
        private val productIdTextView: TextView = itemView.findViewById(R.id.productIdTextView)
        private val employeeIdTextView: TextView = itemView.findViewById(R.id.employeeIdTextView)
        private val orderDateTextView: TextView = itemView.findViewById(R.id.orderDateTextView)
        private val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        private val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        val updateStatusButton: Button = itemView.findViewById(R.id.updateStatusButton)

        fun bind(order: Order) {
            orderIdTextView.text = "Order ID: ${order.orderId}"
            customerIdTextView.text = "Customer ID: ${order.customerId}"
            productIdTextView.text = "Product ID: ${order.productId}"
            employeeIdTextView.text = "Employee ID: ${order.employeeId}"
            orderDateTextView.text = "Order Date: ${order.orderDate}"
            quantityTextView.text = "Quantity: ${order.quantity}"
            statusTextView.text = "Status: ${order.status}"
        }
    }
}
