package com.example.recyclerviewretrofitfrancisco.framework.customer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewretrofitfrancisco.R
import com.example.recyclerviewretrofitfrancisco.databinding.ItemCustomerBinding
import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import com.example.recyclerviewretrofitfrancisco.framework.Constantes
import com.example.recyclerviewretrofitfrancisco.framework.detalleyorders.DetalleYOrdersActivity
import com.example.recyclerviewretrofitfrancisco.framework.SwipeGesture

class CustomerAdapter(
    val context: Context,
    val actions: CustomerActions
) : ListAdapter<Customer, CustomerAdapter.ItemViewHolder>(DiffCallback()) {

    interface CustomerActions {
        fun onDelete(customer: Customer)
        fun onStartSelectMode(customer: Customer)
    }

    private var selectedCustomers = mutableListOf<Customer>()

    private var selectedMode: Boolean = false

    fun startSelectMode() {
        selectedMode = true
        notifyDataSetChanged()
    }
    fun resetSelectMode() {
        selectedMode = false
        selectedCustomers.clear()
        notifyDataSetChanged()
    }
    fun setSelectedItems(customersSeleccionados: List<Customer>) {
        selectedCustomers.clear()
        selectedCustomers.addAll(customersSeleccionados)
        notifyDataSetChanged()
    }


    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCustomerBinding.bind(itemView)


        fun bind(item: Customer) {

            // Configuración del evento de clic largo en el elemento de la lista
            itemView.setOnLongClickListener {
                if (!selectedMode) {
                    val item = getItem(bindingAdapterPosition)
                    toggleSelection(item)
                }
                true // Indica que se ha manejado el clic largo
            }

            // Configuración del evento de clic en el CheckBox
            binding.selected.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                toggleSelection(item)
            }

            // Configuración del evento de clic en cualquier otra parte del elemento (excepto el CheckBox)
            itemView.setOnClickListener {
                itemView.setOnClickListener {
                    if (item.isSelected){
                        val item = getItem(bindingAdapterPosition)
                        toggleSelection(item)
                    }else{
                        val intent = Intent(context, DetalleYOrdersActivity::class.java)
                        intent.putExtra(Constantes.idIntentName, item.id)
                        context.startActivity(intent)
                    }
                }
            }

            with(binding) {
                // Lógica para seleccionar/deseleccionar elementos
                selected.isChecked = item.isSelected
                updateItemBackgroundColor(item)

                tvNombre.text = item.firstName
                tvId.text = item.id.toString()
            }
        }

        private fun updateItemBackgroundColor(item: Customer) {
            itemView.setBackgroundColor(if (item.isSelected) Color.GREEN else Color.TRANSPARENT)
        }

        // Método para cambiar el estado de selección del elemento y actualizar su apariencia
        private fun toggleSelection(item: Customer) {
            item.isSelected = !item.isSelected
            updateItemBackgroundColor(item)

            if (item.isSelected) {
                selectedCustomers.add(item)
                actions.onStartSelectMode(item)
            } else {
                selectedCustomers.remove(item)
                actions.onStartSelectMode(item)
            }
            notifyItemChanged(bindingAdapterPosition)
        }
    }

    val swipeGesture = object : SwipeGesture(context){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT){
                val position = viewHolder.bindingAdapterPosition
                actions.onDelete(currentList[position])
            }
        }
    }
}