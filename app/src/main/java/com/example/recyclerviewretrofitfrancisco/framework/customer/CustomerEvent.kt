package com.example.recyclerviewretrofitfrancisco.framework.customer

import com.example.recyclerviewretrofitfrancisco.domain.model.Customer

sealed class CustomerEvent{

    object GetCustomers : CustomerEvent()

    object StartSelectMode: CustomerEvent()
    object ResetSelectMode: CustomerEvent()

    class SeleccionaCustomer(val  customer: Customer) : CustomerEvent()
}