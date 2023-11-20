package com.example.recyclerviewretrofitfrancisco.data.model

import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import java.time.LocalDate

data class CustomerResponse(
   //Si la variable se llamara distinto al campo del json se tiene que usar la anotcion SerializedName
    val id: Int,
    val firstName: String,
    val lastName: String,
    val birthDate: LocalDate,
    val email: String,
    val phone: String
)

fun CustomerResponse.toCustomer(): Customer =
    Customer(id, firstName, lastName, birthDate, email, phone)
