package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import com.example.recyclerviewretrofitfrancisco.domain.usecases.AddOrderUsecase
import com.example.recyclerviewretrofitfrancisco.domain.usecases.DeleteOrderUsecase
import com.example.recyclerviewretrofitfrancisco.domain.usecases.GetCustomerOrderUsecase
import com.example.recyclerviewretrofitfrancisco.domain.usecases.GetCustomerUsecase
import com.example.recyclerviewretrofitfrancisco.framework.Constantes
import com.example.recyclerviewretrofitfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class DetallesYOrdersViewModel @Inject constructor(
    private val getCustomerOrderUsecase: GetCustomerOrderUsecase,
    private val getCustomerUsecase: GetCustomerUsecase,
    private val deleteOrderUsecase: DeleteOrderUsecase,
    private val addOrderUsecase: AddOrderUsecase
) : ViewModel() {

    private val _uiState = MutableLiveData(DetallesYOrdersState())
    val uiState: LiveData<DetallesYOrdersState> get() = _uiState

    fun handleEvent(event: DetallesYOrdersEvent) {
        when (event) {
            is DetallesYOrdersEvent.GetCustomerOrders -> getCustomerOrder(event.id)
            is DetallesYOrdersEvent.GetCustomer -> getCustomer(event.id)
            is DetallesYOrdersEvent.DeleteOrder -> deleteOrder(event.order)
            DetallesYOrdersEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)
            is DetallesYOrdersEvent.AddOrder -> addOrder(event.id)
        }
    }

    private fun addOrder(id: Int) {
        viewModelScope.launch {
            val response =
                addOrderUsecase.invoke(Order(id, LocalDateTime.now(), 0, Random.nextInt(1, 8)))

            when (response) {
                is NetworkResultt.Error -> _uiState.value =
                    _uiState.value?.copy(error = response.message)

                is NetworkResultt.Loading -> _uiState.value =
                    _uiState.value?.copy(error = Constantes.mensajeCargando)

                is NetworkResultt.Success -> _uiState.value =
                    _uiState.value?.copy(error = Constantes.mensajeOrderAgregada)
            }

            getCustomerOrder(id)
        }
    }

    private fun deleteOrder(order: Order) {
        viewModelScope.launch {
            val response = deleteOrderUsecase.invoke(order.id)

            when (response) {
                is NetworkResultt.Error -> _uiState.value =
                    _uiState.value?.copy(error = response.message)

                is NetworkResultt.Loading -> _uiState.value =
                    _uiState.value?.copy(error = Constantes.mensajeCargando)

                is NetworkResultt.Success -> _uiState.value =
                    _uiState.value?.copy(error = Constantes.mensajeOrderEliminada)
            }


            getCustomerOrder(order.customerId)
        }

    }

    private fun getCustomer(id: Int) {
        viewModelScope.launch {
            val result = getCustomerUsecase.invoke(id)

            when (result) {
                is NetworkResultt.Error -> _uiState.value =
                    _uiState.value?.copy(error = result.message)

                is NetworkResultt.Loading -> _uiState.value =
                    _uiState.value?.copy(error = Constantes.mensajeCargando)

                is NetworkResultt.Success -> result.data?.let { customer ->
                    val updateState = _uiState.value?.copy(customer = customer)
                    _uiState.value = updateState
                }
            }
        }
    }

    private fun getCustomerOrder(id: Int) {
        viewModelScope.launch {
            val result = getCustomerOrderUsecase.invoke(id)

            when (result) {
                is NetworkResultt.Error -> _uiState.value =
                    _uiState.value?.copy(error = result.message)

                is NetworkResultt.Loading -> _uiState.value =
                    _uiState.value?.copy(error = Constantes.mensajeCargando)

                is NetworkResultt.Success -> result.data?.let { orders ->
                    val updateState = _uiState.value?.copy(ordersList = orders)

                    _uiState.value = updateState
                }
            }

        }
    }


}