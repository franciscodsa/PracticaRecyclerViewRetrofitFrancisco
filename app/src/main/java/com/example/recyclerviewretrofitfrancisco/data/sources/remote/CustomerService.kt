package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.recyclerviewretrofitfrancisco.data.model.CustomerResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface CustomerService {
    @GET("customers")
    suspend fun getCustomers():Response<List<CustomerResponse>>

    @GET("customers/{id}")
    suspend fun getCustomer(@Path("id") id : Int) : Response<CustomerResponse>

    @DELETE("customers/{id}")
    suspend fun deleteCustomer(@Path("id") id : Int) : Response<Unit>
}