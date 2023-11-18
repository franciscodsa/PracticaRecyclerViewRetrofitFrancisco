package com.example.recyclerviewretrofitfrancisco.framework.detalleyorders

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetalleYOrdersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        getSelectedCustomerId()
    }

    private fun getSelectedCustomerId(): Int {

        return intent.getIntExtra("CUSTOMER_ID", 0)
    }
}