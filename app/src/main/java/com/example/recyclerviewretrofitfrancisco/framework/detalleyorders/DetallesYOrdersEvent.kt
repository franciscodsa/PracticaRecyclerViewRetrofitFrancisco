package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

sealed class DetallesYOrdersEvent {

    class GetCustomerOrders(val id : Int): DetallesYOrdersEvent()
}