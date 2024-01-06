package com.example.samplemovielistcleanarchitecture.core.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import java.lang.ref.WeakReference

class NetworkUtils(private val weakContext: Application) {

    private val weakApp = WeakReference<Application>(weakContext)

    fun isNetworkAvailable(): Boolean {
        weakApp.get()?.let { context ->
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager?.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        } ?: kotlin.run {
            return false
        }
    }
}