package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewretrofitfrancisco.R
import com.example.recyclerviewretrofitfrancisco.databinding.ActivityDetalleYOrdersBinding
import com.example.recyclerviewretrofitfrancisco.domain.model.Order
import com.example.recyclerviewretrofitfrancisco.framework.customer.CustomerEvent
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
                viewModel.handleEvent(DetallesYOrdersEvent.DeleteOrder(order))
            }

        })

        binding.ordersRecyclerView.adapter = detallesYOrdersAdapter
        binding.ordersRecyclerView.layoutManager=LinearLayoutManager(this)

        val touchHelper = ItemTouchHelper(detallesYOrdersAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.ordersRecyclerView)


        viewModel.handleEvent(DetallesYOrdersEvent.GetCustomerOrders(getSelectedCustomerId()))
        viewModel.handleEvent(DetallesYOrdersEvent.GetCustomer(getSelectedCustomerId()))

        viewModel.uiState.observe(this){state ->
            state.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.handleEvent(DetallesYOrdersEvent.ErrorVisto)
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
        configurarAppBar()
    }

    private fun getSelectedCustomerId(): Int {

        return intent.getIntExtra("CUSTOMER_ID", 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_menu, menu)
        return true
    }
    private fun configurarAppBar() {
        binding.topAppBarOrders.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.añadirMenuBarAction-> {
                    viewModel.handleEvent(DetallesYOrdersEvent.AddOrder(getSelectedCustomerId()))

                    true
                }

                else -> false
            }

        }
    }
}