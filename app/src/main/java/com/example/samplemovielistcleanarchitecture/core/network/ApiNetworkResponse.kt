package com.example.samplemovielistcleanarchitecture.core.network

sealed class ApiNetworkResponse<T> {
    data class Success<T>(val t: T): ApiNetworkResponse<T>()
    data class Failed(val type: NetworkResponseFailedType, val message: String): ApiNetworkResponse<Nothing>()
}

enum class NetworkResponseFailedType {
    NO_INTERNET,
    SERVER_ERROR
}
