package com.example.recyclerviewretrofitfrancisco.framework

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecyclerViewRetrofit : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}