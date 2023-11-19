package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import com.example.recyclerviewretrofitfrancisco.domain.model.Order

data class DetallesYOrdersState (
    val ordersList : List<Order> = emptyList(),
    val error : String? = null
)

