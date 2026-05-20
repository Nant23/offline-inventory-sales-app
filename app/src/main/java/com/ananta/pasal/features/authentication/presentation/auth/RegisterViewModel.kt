package com.ananta.pasal.features.authentication.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ananta.pasal.features.authentication.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState

    fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        role: String,
        shopName: String? = null,
        shopAddress: String? = null,
        deliveryAddress: String? = null
    ) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            val result = authRepository.registerWithEmail(
                fullName, email, password, phoneNumber,
                role, shopName, shopAddress, deliveryAddress
            )
            _registerState.value = result.fold(
                onSuccess = { AuthState.Success(it) },
                onFailure = { AuthState.Error(it.message ?: "Registration failed") }
            )
        }
    }

    fun resetState() {
        _registerState.value = AuthState.Idle
    }
}