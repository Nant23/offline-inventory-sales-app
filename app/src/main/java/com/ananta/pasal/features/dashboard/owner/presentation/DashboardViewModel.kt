// features/owner/presentation/dashboard/DashboardViewModel.kt
package com.ananta.pasal.features.owner.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ananta.pasal.features.authentication.domain.repository.AuthRepository
import com.ananta.pasal.features.dashboard.owner.domain.repository.OwnerRepository
import com.ananta.pasal.features.dashboard.owner.presentation.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val ownerRepository: OwnerRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            // get current owner uid from auth
            authRepository.currentUser
                .filterNotNull()
                .take(1)
                .collect { user ->
                    combine(
                        ownerRepository.getProducts(user.uid),
                        ownerRepository.getOrders(user.uid),
                        ownerRepository.getDashboardStats(user.uid)
                    ) { products, orders, stats ->
                        DashboardUiState.Success(
                            products = products,
                            recentOrders = orders.take(5), // show 5 most recent
                            stats = stats,
                            ownerName = user.fullName,
                            shopName = user.shopName ?: "My Shop"
                        )
                    }.collect { state ->
                        _uiState.value = state
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}

