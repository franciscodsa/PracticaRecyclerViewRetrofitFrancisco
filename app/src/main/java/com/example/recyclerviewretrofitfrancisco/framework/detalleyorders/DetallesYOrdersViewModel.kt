package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewretrofitfrancisco.domain.usecases.GetCustomerOrderUsecase
import com.example.recyclerviewretrofitfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesYOrdersViewModel @Inject constructor(private val getCustomerOrderUsecase: GetCustomerOrderUsecase) :
    ViewModel() {

    private val _uiState = MutableLiveData(DetallesYOrdersState())
    val uiState: LiveData<DetallesYOrdersState> get() = _uiState
    fun handleEvent(event: DetallesYOrdersEvent) {
        when (event) {
            is DetallesYOrdersEvent.GetCustomerOrders -> getCustomerOrder(event.id)
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