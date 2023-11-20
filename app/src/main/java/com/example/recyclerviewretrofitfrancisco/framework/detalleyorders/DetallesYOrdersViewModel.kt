package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import com.example.recyclerviewretrofitfrancisco.domain.usecases.DeleteOrderUsecase
import com.example.recyclerviewretrofitfrancisco.domain.usecases.GetCustomerOrderUsecase
import com.example.recyclerviewretrofitfrancisco.domain.usecases.GetCustomerUsecase
import com.example.recyclerviewretrofitfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesYOrdersViewModel @Inject constructor(
    private val getCustomerOrderUsecase: GetCustomerOrderUsecase,
    private val getCustomerUsecase: GetCustomerUsecase,
    private val deleteOrderUsecase: DeleteOrderUsecase
) :
    ViewModel() {

    private val _uiState = MutableLiveData(DetallesYOrdersState())
    val uiState: LiveData<DetallesYOrdersState> get() = _uiState

    fun handleEvent(event: DetallesYOrdersEvent) {
        when (event) {
            is DetallesYOrdersEvent.GetCustomerOrders -> getCustomerOrder(event.id)
            is DetallesYOrdersEvent.GetCustomer -> getCustomer(event.id)
            is DetallesYOrdersEvent.DeleteOrder ->deleteOrder(event.order)
            DetallesYOrdersEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)
            is DetallesYOrdersEvent.AddOrder -> TODO()
        }
    }

    private fun deleteOrder(order: Order) {
        viewModelScope.launch {
            val response = deleteOrderUsecase.invoke(order.id)

            when(response){
                is NetworkResultt.Error -> TODO()
                is NetworkResultt.Loading -> TODO()
                is NetworkResultt.Success -> _uiState.value = _uiState.value?.copy(error = "Order eliminada")
            }

            getCustomerOrder(order.customerId)
        }

    }

    private fun getCustomer(id : Int){
        viewModelScope.launch {
            val result = getCustomerUsecase.invoke(id)

            when(result){
                is NetworkResultt.Error -> TODO()
                is NetworkResultt.Loading -> TODO()
                is NetworkResultt.Success -> result.data?.let {customer ->
                    val updateState = _uiState.value?.copy(customer = customer)
                    _uiState.value = updateState
                }
            }
        }
    }
    private fun getCustomerOrder(id: Int) {
        viewModelScope.launch {
            val result = getCustomerOrderUsecase.invoke(id)

            when (result){
                is NetworkResultt.Error -> _uiState.value = _uiState.value?.copy(error = "error")
                is NetworkResultt.Loading -> TODO()
                is NetworkResultt.Success -> result.data?.let { orders ->
                    val updateState = _uiState.value?.copy(ordersList = orders)

                    _uiState.value = updateState
                }
            }

        }
    }


}