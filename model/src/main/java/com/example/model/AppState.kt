package com.example.model

import com.example.model.dto.Pokemon

sealed class AppState {
    data class Success(val data: List<Pokemon>) : AppState()
    data class Loading(val progress: Int?) : AppState()
    data class Error(val error: Throwable) : AppState()
}