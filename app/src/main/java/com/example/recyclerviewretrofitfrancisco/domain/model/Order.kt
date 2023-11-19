package com.example.recyclerviewretrofitfrancisco.domain.model

import java.time.LocalDateTime

data class Order (
    val customerId: Int,
    val dateTime : LocalDateTime,
    val id : Int,
    val table: Int
)