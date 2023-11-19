package com.example.recyclerviewretrofitfrancisco.domain.usecases

import com.example.recyclerviewretrofitfrancisco.data.repositories.CustomerRepository
import javax.inject.Inject

class GetCustomerUsecase @Inject constructor(private val customerRepository: CustomerRepository){
    suspend operator fun invoke(id : Int) = customerRepository.getCustomer(id)
}