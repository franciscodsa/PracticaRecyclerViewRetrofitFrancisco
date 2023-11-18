package com.example.recyclerviewretrofitfrancisco.framework.customer

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewretrofitfrancisco.R
import com.example.recyclerviewretrofitfrancisco.databinding.DetalleCustomerBinding
import com.example.recyclerviewretrofitfrancisco.domain.model.Customer

class CustomerAdapter(
    val context: Context,
    val actions: CustomerActions
) : ListAdapter<Customer, CustomerAdapter.ItemViewHolder>(DiffCallback()) {

    interface CustomerActions{
        fun onDelete(customer : Customer)
        fun onStartSelectMode(customer : Customer)
        fun itemWasClicked(customer : Customer)
    }

    private var selectedCustomers = mutableListOf<Customer>()
    private var selectedMode: Boolean = false

    fun startSelectedMode(){
        selectedMode = true
        notifyDataSetChanged()
    }

    fun resetSelectedMode(){
        selectedMode = false
        selectedCustomers.clear()
        notifyDataSetChanged()
    }

    fun setSelectedItems(customersSeleccionados : List<Customer>){
        selectedCustomers.clear()
        selectedCustomers.addAll(customersSeleccionados)
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
            LayoutInflater.from(parent.context).inflate(R.layout.detalle_customer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = with(holder){
        val item = getItem(position)
        bind(item)
    }

    inner class ItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val binding = DetalleCustomerBinding.bind(itemView)


        fun bind(item : Customer){

            /*itemView.setOnClickListener {
                if (!selectedMode) {
                    val item = getItem(bindingAdapterPosition)
                    actions.itemHasClicked(item)
                    // Aquí inicia la nueva actividad
                }
            }*/
            // Configuración del evento de clic largo en el elemento de la lista
            itemView.setOnLongClickListener {
                val item = getItem(bindingAdapterPosition)
                toggleSelection(item)
                true // Indicar que se ha manejado el clic largo
            }

            // Configuración del evento de clic en el CheckBox
            binding.selected.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                toggleSelection(item)
                actions.onStartSelectMode(item)
            }

            // Configuración del evento de clic en cualquier otra parte del elemento (excepto el CheckBox)
            itemView.setOnClickListener {
                if (!selectedMode) {
                    val item = getItem(bindingAdapterPosition)
                    actions.onStartSelectMode(item)
                    // Aquí inicia la nueva actividad
                }
            }

            with(binding){
                // Lógica para seleccionar/deseleccionar elementos
                selected.isChecked = item.isSelected
                updateItemBackgroundColor(item)


                tvNombre.text = item.firstName
                tvId.text= item.id.toString()
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
            } else {
                selectedCustomers.remove(item)
            }
            notifyItemChanged(bindingAdapterPosition)
        }
    }
}