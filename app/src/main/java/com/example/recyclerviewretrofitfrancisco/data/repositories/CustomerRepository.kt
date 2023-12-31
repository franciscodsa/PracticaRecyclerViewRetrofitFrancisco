package com.example.recyclerviewretrofitfrancisco.data.repositories

import com.example.recyclerviewretrofitfrancisco.data.sources.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class CustomerRepository @Inject constructor(private val remoteDataSource: RemoteDataSource){

    suspend fun getCustomers() = withContext(Dispatchers.IO){remoteDataSource.getCustomers()}

    suspend fun getCustomer(id : Int) = withContext(Dispatchers.IO){remoteDataSource.getCustomer(id)}

    suspend fun deleteCustomer(id : Int) = withContext(Dispatchers.IO){remoteDataSource.deleteCustomer(id)}
}