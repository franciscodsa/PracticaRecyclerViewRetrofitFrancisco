package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.recyclerviewretrofitfrancisco.data.model.BaseApiResponse
import com.example.recyclerviewretrofitfrancisco.data.model.toCustomer
import com.example.recyclerviewretrofitfrancisco.data.model.toOrder
import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import com.example.recyclerviewretrofitfrancisco.utils.NetworkResultt
import java.lang.Exception
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val customerService: CustomerService,
    private val orderService: OrderService
) :
    BaseApiResponse() {
    suspend fun getCustomers(): NetworkResultt<List<Customer>> {
        try {
            val response = customerService.getCustomers()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val customers = it.map { customerResponse ->
                        customerResponse.toCustomer()
                    }
                    return NetworkResultt.Success(customers)
                }
                return error("No data")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                return error("Error ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun getCustomer(id: Int): NetworkResultt<Customer> {
        try {
            val response = customerService.getCustomer(id)

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val customer = it.toCustomer()
                    return NetworkResultt.Success(customer)
                }

                return error("No data")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                return error("Error ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun deleteCustomer(id: Int) : NetworkResultt<Unit>{
        try {
            val response = customerService.deleteCustomer(id)

            if (response.isSuccessful){
                return NetworkResultt.Success(Unit)
            }else{
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                return error("Error ${response.code()} : $errorMessage")
            }
        }catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun getCustomerOrders(id: Int): NetworkResultt<List<Order>> {
        try {
            val response = orderService.getCustomerOrders(id)

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val orders = it.map { orderResponse ->
                        orderResponse.toOrder()
                    }
                    return NetworkResultt.Success(orders)
                }
                return error("No data")
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                return error("Error ${response.code()} : $errorMessage")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}