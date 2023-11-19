package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import java.time.LocalDate

data class DetallesYOrdersState (
    val customer: Customer = Customer(0, "", "", LocalDate.of(100,1,1), "", ""),
    val ordersList : List<Order> = emptyList(),
    val error : String? = null
)

