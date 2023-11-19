package com.example.recyclerviewretrofitfrancisco.domain.usecases

import com.example.recyclerviewretrofitfrancisco.data.repositories.OrderRepository
import javax.inject.Inject

class GetCustomerOrderUsecase @Inject constructor(private val orderRepository: OrderRepository) {

    suspend operator fun invoke(id : Int) = orderRepository.getCustomerOrders(id)
}