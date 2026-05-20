package com.ananta.pasal.features.authentication.presentation.auth

import com.ananta.pasal.features.authentication.domain.model.User

// sealed class for UI state
sealed class AuthState {
    object Idle    : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}