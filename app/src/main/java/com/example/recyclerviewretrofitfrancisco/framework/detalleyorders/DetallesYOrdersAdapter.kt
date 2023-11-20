package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewretrofitfrancisco.R
import com.example.recyclerviewretrofitfrancisco.databinding.ItemOrderBinding
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import com.example.recyclerviewretrofitfrancisco.framework.SwipeGesture

class DetallesYOrdersAdapter(
    val context: Context,
    val actions: DetallesYOrdersActions
) : ListAdapter<Order, DetallesYOrdersAdapter.ItemViewHolder>(DiffCallback()) {

    fun interface DetallesYOrdersActions {
        fun onDelete(order: Order)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemOrderBinding.bind(itemView)

        fun bind(item: Order) {
            with(binding) {
                tableId.text = item.table.toString()
                idOrder.text = item.id.toString()
                dateTimeOrder.text = item.dateTime.toString()
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        with(holder) {
            val item = getItem(position)
            bind(item)
        }
    }

    val swipeGesture = object : SwipeGesture(context) {

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT) {
                val position = viewHolder.bindingAdapterPosition
                actions.onDelete(currentList[position])
            }
        }
    }
}