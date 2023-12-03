package com.example.nibhamaharjan_mapd711_assignment4
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nibhamaharjan_mapd711_assignment4.model.Order

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdTextView: TextView = itemView.findViewById(R.id.order_id_text_view)
        val customerIdTextView: TextView = itemView.findViewById(R.id.customer_id_text_view)
        val productIdTextView: TextView = itemView.findViewById(R.id.product_id_text_view)
        val employeeIdTextView: TextView = itemView.findViewById(R.id.employee_id_text_view)
        val orderDateTextView: TextView = itemView.findViewById(R.id.order_date_text_view)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantity_text_view)
        val statusTextView: TextView = itemView.findViewById(R.id.status_text_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrder = orders[position]
        holder.orderIdTextView.text = "Order ID: ${currentOrder.orderId}"
        holder.customerIdTextView.text = "Customer ID: ${currentOrder.customerId}"
        holder.productIdTextView.text = "Product ID: ${currentOrder.productId}"
        holder.employeeIdTextView.text = "Employee ID: ${currentOrder.employeeId}"
        holder.orderDateTextView.text = "Order Date: ${currentOrder.orderDate}"
        holder.quantityTextView.text = "Quantity: ${currentOrder.quantity}"
        holder.statusTextView.text = "Status: ${currentOrder.status}"
        // Bind other order details to their respective TextViews
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}
