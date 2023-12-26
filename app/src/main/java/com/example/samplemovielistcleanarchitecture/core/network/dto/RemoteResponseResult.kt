package com.example.samplemovielistcleanarchitecture.core.network.dto

sealed class RemoteResponseResult<T> {
    data class Success<T>(val t: T) : RemoteResponseResult<T>()
    data class Failed<T>(val type: RemoteResponseFailedType, val message: String) :
        RemoteResponseResult<T>()

    fun isSuccess(): Boolean {
        return Success<T>::javaClass == this.javaClass
    }

    fun parseToSuccessClass(): Success<T> {
        return (this as Success<T>)
    }
}

enum class RemoteResponseFailedType {
    NO_INTERNET, SERVER_ERROR, DATA_ERROR
}
