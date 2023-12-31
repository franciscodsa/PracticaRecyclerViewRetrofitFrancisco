package com.example.recyclerviewretrofitfrancisco.data.repositories

import com.example.recyclerviewretrofitfrancisco.data.sources.remote.RemoteDataSource
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class OrderRepository @Inject constructor(private val remoteDataSource: RemoteDataSource){

    suspend fun getCustomerOrders(id : Int) = withContext(Dispatchers.IO){
        remoteDataSource.getCustomerOrders(id)
    }

    suspend fun deleteOrder(id : Int) = withContext(Dispatchers.IO){
        remoteDataSource.deleteOrder(id)
    }

    suspend fun addOrder(order : Order) = withContext(Dispatchers.IO){
        remoteDataSource.addOrder(order)
    }
}