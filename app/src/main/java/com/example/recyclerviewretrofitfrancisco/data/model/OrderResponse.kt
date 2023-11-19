package com.example.recyclerviewretrofitfrancisco.data.model

import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import java.time.LocalDateTime

data class OrderResponse (
    val customerId: Int,
    val dateTime : LocalDateTime,
    val id : Int,
    val table: Int
)

fun OrderResponse.toOrder() : Order = Order(customerId, dateTime, id, table)

