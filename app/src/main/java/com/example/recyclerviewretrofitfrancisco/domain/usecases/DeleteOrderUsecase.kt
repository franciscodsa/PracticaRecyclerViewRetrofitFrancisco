package com.example.recyclerviewretrofitfrancisco.domain.usecases

import com.example.recyclerviewretrofitfrancisco.data.repositories.OrderRepository
import javax.inject.Inject

class DeleteOrderUsecase @Inject constructor(private val orderRepository: OrderRepository){

    suspend operator fun invoke(id: Int) = orderRepository.deleteOrder(id)
}