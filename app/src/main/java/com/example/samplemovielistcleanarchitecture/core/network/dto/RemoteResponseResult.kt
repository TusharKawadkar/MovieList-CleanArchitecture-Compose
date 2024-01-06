package com.example.samplemovielistcleanarchitecture.core.network.dto

sealed class RemoteResponseResult<T> {
    data class Success<T>(val t: T) : RemoteResponseResult<T>()
    data class Failed<T>(val type: CommonFailureType, val message: String) : RemoteResponseResult<T>()
}

enum class CommonFailureType {
    NO_INTERNET, SERVER_ERROR, DATA_ERROR
}




