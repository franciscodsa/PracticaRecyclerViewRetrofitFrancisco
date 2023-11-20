package com.example.recyclerviewretrofitfrancisco.domain.usecases

import com.example.recyclerviewretrofitfrancisco.data.repositories.OrderRepository
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import javax.inject.Inject

class AddOrderUsecase @Inject constructor(private val orderRepository: OrderRepository){
    suspend operator fun invoke(order : Order) = orderRepository.addOrder(order)
}