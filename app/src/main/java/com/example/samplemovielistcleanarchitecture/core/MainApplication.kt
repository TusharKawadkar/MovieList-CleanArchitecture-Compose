package com.example.samplemovielistcleanarchitecture.core

import android.app.Application
import com.example.samplemovielistcleanarchitecture.core.repository.AppRepository

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppRepository.initInstance(this)
    }
}