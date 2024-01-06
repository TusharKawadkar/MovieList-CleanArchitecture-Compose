package com.example.samplemovielistcleanarchitecture.core.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.samplemovielistcleanarchitecture.core.MainApplication
import java.lang.ref.WeakReference

class NetworkUtils(private val weakContext: WeakReference<MainApplication>) {

    fun isNetworkAvailable(): Boolean {
        weakContext.get()?.let { context ->
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager?.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        } ?: kotlin.run {
            return false
        }
    }
}