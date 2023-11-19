package com.example.recyclerviewretrofitfrancisco.framework.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewretrofitfrancisco.R
import com.example.recyclerviewretrofitfrancisco.databinding.ActivityCustomerBinding
import com.example.recyclerviewretrofitfrancisco.domain.model.Customer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerBinding

    private val viewModel: CustomerViewModel by viewModels()

    private lateinit var customerAdapter: CustomerAdapter

    private var anteriorState : CustomerState? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)



        customerAdapter = CustomerAdapter(this, object : CustomerAdapter.CustomerActions {
            override fun onDelete(customer: Customer) {
                TODO("Not yet implemented")
            }

            override fun onStartSelectMode(customer: Customer) {
                viewModel.handleEvent(CustomerEvent.StartSelectMode)
                viewModel.handleEvent(CustomerEvent.SeleccionaCustomer(customer))
            }

            override fun itemWasClicked(customer: Customer) {
                TODO("Not yet implemented")
            }
        })

        binding.customerRecycleView.adapter = customerAdapter
        binding.customerRecycleView.layoutManager = LinearLayoutManager(this)




        viewModel.handleEvent(CustomerEvent.GetCustomers)

        configurarAppBar()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.customer_menu, menu)
        return true
    }

    private fun configurarAppBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.borrarMenuBarAction -> {
                    deleteSelectedCustomers()
                    true
                }

                else -> false
            }

        }
    }

    private fun deleteSelectedCustomers() {
        TODO("Not yet implemented")
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            if (state.customersList!=anteriorState?.customersList && state.customersList.isNotEmpty()){
                val customersList = state.customersList
                customerAdapter.submitList(customersList)
            }

            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                //TODO : ADD ERRORVISTO EVENT
            }


        }
    }


}