package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.recyclerviewretrofitfrancisco.data.model.OrderResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrderService {

    @GET("orders/customer")
    suspend fun getCustomerOrders(@Query("id") id : Int) : Response<List<OrderResponse>>

}