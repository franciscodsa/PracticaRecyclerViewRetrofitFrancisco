package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import com.example.recyclerviewretrofitfrancisco.domain.model.Order

sealed class DetallesYOrdersEvent {

    class GetCustomerOrders(val id : Int): DetallesYOrdersEvent()

    class GetCustomer(val id : Int): DetallesYOrdersEvent()

    class DeleteOrder(val order : Order ) : DetallesYOrdersEvent()
    class AddOrder(val id: Int) : DetallesYOrdersEvent()

    object ErrorVisto :  DetallesYOrdersEvent()


}