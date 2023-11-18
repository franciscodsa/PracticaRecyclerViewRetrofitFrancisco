package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.recyclerviewretrofitfrancisco.data.model.CustomerResponse
import retrofit2.Response
import retrofit2.http.GET

interface CustomerService {
    @GET("customers")
    suspend fun getCustomers():Response<List<CustomerResponse>>
}