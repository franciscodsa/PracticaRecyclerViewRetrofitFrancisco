package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewretrofitfrancisco.databinding.ActivityDetalleYOrdersBinding
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleYOrdersActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetalleYOrdersBinding

    private val viewModel : DetallesYOrdersViewModel by viewModels()

    private lateinit var detallesYOrdersAdapter: DetallesYOrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleYOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBarOrders)



        detallesYOrdersAdapter = DetallesYOrdersAdapter(this, object :DetallesYOrdersAdapter.DetallesYOrdersActions{
            override fun onDelete(order: Order) {
                TODO("Not yet implemented")
            }

        })

        binding.ordersRecyclerView.adapter = detallesYOrdersAdapter
        binding.ordersRecyclerView.layoutManager=LinearLayoutManager(this)

        viewModel.handleEvent(DetallesYOrdersEvent.GetCustomerOrders(getSelectedCustomerId()))
        viewModel.handleEvent(DetallesYOrdersEvent.GetCustomer(getSelectedCustomerId()))

        viewModel.uiState.observe(this){state ->
            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                //TODO : ADD ERRORVISTO EVENT
            }

            with(binding){
                firstName.setText(state.customer.firstName)
                lastNameTv.setText(state.customer.lastName)
                dateOfBirthTv.setText(state.customer.birthDate.toString())
                emailTv.setText(state.customer.email)
                phoneTv.setText(state.customer.phone)
            }

            val ordersList = state.ordersList
            detallesYOrdersAdapter.submitList(ordersList)
        }
    }

    private fun getSelectedCustomerId(): Int {

        return intent.getIntExtra("CUSTOMER_ID", 0)
    }
}