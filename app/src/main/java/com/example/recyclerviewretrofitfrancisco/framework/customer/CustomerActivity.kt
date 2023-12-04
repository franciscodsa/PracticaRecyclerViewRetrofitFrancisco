package com.example.recyclerviewretrofitfrancisco.framework.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
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

    private val callback by lazy {
        configContextBar()
    }

    private var actionMode : androidx.appcompat.view.ActionMode?=null

    private fun configContextBar() =
        object : androidx.appcompat.view.ActionMode.Callback{


            override fun onCreateActionMode(
                mode: androidx.appcompat.view.ActionMode?,
                menu: Menu?
            ): Boolean {
                menuInflater.inflate(R.menu.context_bar, menu)
                binding.topAppBar.visibility = View.GONE
                return true
            }

            override fun onPrepareActionMode(
                mode: androidx.appcompat.view.ActionMode?,
                menu: Menu?
            ): Boolean {
                return false
            }

            override fun onActionItemClicked(
                mode: androidx.appcompat.view.ActionMode?,
                item: MenuItem?
            ): Boolean {
                return when (item?.itemId){
                    R.id.borrarContextBar -> {
                        viewModel.handleEvent(CustomerEvent.DeleteSelectedCustomers)
                        true
                    }

                    else -> {false}
                }
            }

            override fun onDestroyActionMode(mode: androidx.appcompat.view.ActionMode?) {
                binding.topAppBar.visibility = View.VISIBLE
                viewModel.handleEvent(CustomerEvent.ResetSelectMode)
            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)



        customerAdapter = CustomerAdapter(this, object : CustomerAdapter.CustomerActions {
            override fun onDelete(customer: Customer) {
                viewModel.handleEvent(CustomerEvent.DeleteCustomer(customer))
            }

            override fun onStartSelectMode(customer: Customer) {
                viewModel.handleEvent(CustomerEvent.StartSelectMode)
                viewModel.handleEvent(CustomerEvent.SeleccionaCustomer(customer))
            }
        })

        binding.customerRecycleView.adapter = customerAdapter
        binding.customerRecycleView.layoutManager = LinearLayoutManager(this)

        val touchHelper = ItemTouchHelper(customerAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.customerRecycleView)


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
                    viewModel.handleEvent(CustomerEvent.DeleteSelectedCustomers)

                    true
                }
                else -> false
            }

        }
    }


    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            if (state.customersList!=anteriorState?.customersList && state.customersList.isNotEmpty()){
                val customersList = state.customersList
                customerAdapter.submitList(customersList)
            }

            actionMode?.title = "${state.customersSeleccionados.size} "+ "selected"
            if (state.customersSeleccionados != anteriorState?.customersSeleccionados){
                customerAdapter.setSelectedItems(state.customersSeleccionados)
            }


            if (state.selectedMode!= anteriorState?.selectedMode){
                if (state.selectedMode){
                    customerAdapter.startSelectMode()
                    startSupportActionMode(callback)?.let {
                        actionMode = it;
                    }
                }else{
                    customerAdapter.resetSelectMode()
                    actionMode?.finish()
                    binding.topAppBar.visibility = View.VISIBLE
                }
            }

            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.handleEvent(CustomerEvent.ErrorVisto)
            }

            anteriorState = state
        }
    }


}