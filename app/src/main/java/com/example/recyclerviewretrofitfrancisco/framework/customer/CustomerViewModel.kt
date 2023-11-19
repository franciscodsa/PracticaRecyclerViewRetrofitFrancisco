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

    private val customersList = mutableListOf<Customer>()

    private var selectedCustomers = mutableListOf<Customer>()


    private val _uiState = MutableLiveData(CustomerState())
    val uiState: LiveData<CustomerState> get() = _uiState


    fun handleEvent(event: CustomerEvent) {
        when (event) {
            CustomerEvent.GetCustomers -> {
                getCustomers()
            }

            CustomerEvent.ResetSelectMode -> {
                TODO()
            }

            is CustomerEvent.SeleccionaCustomer -> {
                seleccionaCustomer(event.customer)
            }

            CustomerEvent.StartSelectMode -> {
                _uiState.value = _uiState.value?.copy(selectedMode = true)
            }

            CustomerEvent.DeleteSelectedCustomers -> deleteCustomers()
            CustomerEvent.ErrorVisto -> _uiState.value = _uiState.value?.copy(error = null)
        }
    }

    private fun deleteCustomers() {
        viewModelScope.launch {
            uiState.value?.customersSeleccionados?.forEach { customer ->
                // Obtener el ID del cliente y llamar al caso de uso para eliminarlo
                val customerId = customer.id

                deleteCustomer(customerId)
            }
            // Después de eliminar todos los clientes seleccionados, limpiar la lista de seleccionados en el estado
            _uiState.value = _uiState.value?.copy(customersSeleccionados = emptyList())
            getCustomers()
        }
    }

    private suspend fun deleteCustomer(customerId: Int) {
        val result =
            deleteCustomerUsecase.invoke(customerId) // Asumiendo que 'invoke' toma el ID como parámetro para eliminar el cliente

        // Aquí puedes manejar el resultado si es necesario, por ejemplo, actualizar el estado en caso de error, etc.
        when (result) {
            is NetworkResultt.Error -> {
                // Manejar el error si es necesario
                _uiState.value =
                    _uiState.value?.copy(error = "Error al eliminar cliente con ID: $customerId")
            }

            is NetworkResultt.Success -> {
                _uiState.value = _uiState.value?.copy(error = "Customer eliminado")
                // Realizar acciones necesarias si la eliminación fue exitosa
            }
            // Agregar manejo para NetworkResultt.Loading si es necesario
            is NetworkResultt.Loading -> TODO()
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