package com.example.samplemovielistcleanarchitecture.data.dto

import com.example.samplemovielistcleanarchitecture.data.entities.MovieEntityDto

sealed class NetworkResponseDto {
    data class Success(val data: List<MovieEntityDto>): NetworkResponseDto()
    data class Failed(val type: NetworkResponseFailureType, val message: String): NetworkResponseDto()
}

enum class NetworkResponseFailureType{
    NO_INTERNET, SERVER_ERROR
}

