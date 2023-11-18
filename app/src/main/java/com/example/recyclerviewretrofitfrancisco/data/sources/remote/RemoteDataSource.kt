package com.example.recyclerviewretrofitfrancisco.data.sources.remote

import com.example.recyclerviewretrofitfrancisco.data.model.BaseApiResponse
import com.example.recyclerviewretrofitfrancisco.data.model.toCustomer
import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import com.example.recyclerviewretrofitfrancisco.utils.NetworkResultt
import java.lang.Exception
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val customerService: CustomerService) :
    BaseApiResponse() {
        suspend fun getCustomers() : NetworkResultt<List<Customer>>{
            try {
                val response = customerService.getCustomers()

                if (response.isSuccessful){
                    val body = response.body()
                    body?.let {
                        val customers = it.map{
                            customerResponse -> customerResponse.toCustomer()
                        }
                        return NetworkResultt.Success(customers)
                    }
                    return error("No data")
                }else{
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    return error("Error ${response.code()} : $errorMessage")
                }
            }catch (e : Exception){
                return error(e.message ?: e.toString())
            }
        }
}