package com.example.recyclerviewretrofitfrancisco.framework.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import com.example.recyclerviewretrofitfrancisco.domain.usecases.DeleteCustomerUsecase
import com.example.recyclerviewretrofitfrancisco.domain.usecases.GetAllCustomersUseCase
import com.example.recyclerviewretrofitfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val getAllCustomersUseCase: GetAllCustomersUseCase,
    private val deleteCustomerUsecase: DeleteCustomerUsecase
) :
    ViewModel() {


    private var selectedCustomers = mutableListOf<Customer>()


    private val _uiState = MutableLiveData(CustomerState())
    val uiState: LiveData<CustomerState> get() = _uiState


    fun handleEvent(event: CustomerEvent) {
        when (event) {
            CustomerEvent.GetCustomers -> {
                getCustomers()
            }

            CustomerEvent.ResetSelectMode -> {
                resetSelectMode()
            }

            is CustomerEvent.SeleccionaCustomer -> {
                seleccionaCustomer(event.customer)
            }

            CustomerEvent.StartSelectMode -> {
                _uiState.value = _uiState.value?.copy(selectedMode = true)
            }

            CustomerEvent.DeleteSelectedCustomers -> deleteCustomers(selectedCustomers)

            CustomerEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)

            is CustomerEvent.DeleteCustomer -> deleteCustomer(event.customer)
        }
    }

    private fun deleteCustomers(customers: List<Customer>) {
        viewModelScope.launch {
            customers.forEach { customer ->
                var result = deleteCustomerUsecase.invoke(customer.id)

                when(result){
                    is NetworkResultt.Error -> _uiState.value = _uiState.value?.copy(error = result.message)
                    is NetworkResultt.Success -> _uiState.value = _uiState.value?.copy(error = "eliminado(s)")
                    is NetworkResultt.Loading -> TODO()
                }
            }
            resetSelectMode()
            getCustomers()
        }

    }

    private fun resetSelectMode() {
        selectedCustomers.clear()
        _uiState.value = _uiState.value?.copy(selectedMode = false, customersSeleccionados = selectedCustomers)
    }

    private fun deleteCustomer(customer: Customer) {
       viewModelScope.launch {
           val result = deleteCustomerUsecase.invoke(customer.id)

           when(result){
               is NetworkResultt.Error -> TODO()
               is NetworkResultt.Loading -> TODO()
               is NetworkResultt.Success -> _uiState.value = _uiState.value?.copy(error = "Customer eliminado")
           }
           getCustomers()
       }

    }


    private fun seleccionaCustomer(customer: Customer) {
        if (isSeleceted(customer)) {
            selectedCustomers.remove(customer)
        } else {
            selectedCustomers.add(customer)
        }

        _uiState.value = _uiState.value?.copy(customersSeleccionados = selectedCustomers)
    }

    private fun isSeleceted(customer: Customer): Boolean {
        return selectedCustomers.contains(customer)
    }

    private fun getCustomers() {

        viewModelScope.launch {
            val result = getAllCustomersUseCase.invoke()

            when (result) {
                //TODO : REVISA LO DEL RESULT MESSAGE
                is NetworkResultt.Error -> _uiState.value =
                    _uiState.value?.copy(error = "Error al recuperar customers")

                is NetworkResultt.Loading -> TODO()
                is NetworkResultt.Success -> {
                    result.data?.let { customers ->
                        val updatedState = _uiState.value?.copy(customersList = customers)

                        _uiState.value = updatedState
                    }
                }
            }
        }
    }

}