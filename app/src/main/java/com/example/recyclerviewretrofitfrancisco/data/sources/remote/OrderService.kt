package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.recyclerviewretrofitfrancisco.data.model.OrderResponse
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderService {

    @GET("orders/customer")
    suspend fun getCustomerOrders(@Query("id") id : Int) : Response<List<OrderResponse>>

    @POST("orders")
    suspend fun añadirOrder(@Body order: Order) : Response<OrderResponse>

    @DELETE("orders/{id}")
    suspend fun deleteOrder(@Path("id") id : Int) : Response <Unit>

}