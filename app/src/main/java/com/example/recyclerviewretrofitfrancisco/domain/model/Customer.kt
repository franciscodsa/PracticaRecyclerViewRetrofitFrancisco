package com.example.recyclerviewretrofitfrancisco.domain.model

import java.time.LocalDate

data class Customer (
    val id: Int,
    var firstName: String,
    var lastName: String,
    var birthDate : LocalDate,
    var email: String,
    var phone: String,
    var isSelected: Boolean = false
)
