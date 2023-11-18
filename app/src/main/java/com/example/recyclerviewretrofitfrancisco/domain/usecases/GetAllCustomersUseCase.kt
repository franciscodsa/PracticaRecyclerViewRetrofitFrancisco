package com.example.recyclerviewretrofitfrancisco.domain.usecases



import com.example.recyclerviewretrofitfrancisco.data.repositories.CustomerRepository
import javax.inject.Inject

class GetAllCustomersUseCase @Inject constructor(private val customerRepository: CustomerRepository) {

    suspend operator fun invoke() = customerRepository.getCustomers()

}