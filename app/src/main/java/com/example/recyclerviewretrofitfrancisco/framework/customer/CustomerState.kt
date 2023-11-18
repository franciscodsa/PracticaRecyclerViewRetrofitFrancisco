package com.example.recyclerviewretrofitfrancisco.framework.customer

import com.example.recyclerviewretrofitfrancisco.domain.model.Customer

data class CustomerState(
    val customersList : List<Customer> = emptyList(),
    val customersSeleccionados : List<Customer> = emptyList(),
    val selectedMode : Boolean = false,
    val error: String? = null
)