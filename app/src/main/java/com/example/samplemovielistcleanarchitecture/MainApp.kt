package com.example.samplemovielistcleanarchitecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
final class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}