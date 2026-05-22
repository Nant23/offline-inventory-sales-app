package com.ananta.pasal.features.dashboard.owner.presentation

import com.ananta.pasal.features.dashboard.owner.domain.model.DashBoardStats
import com.ananta.pasal.source.local.model.Order
import com.ananta.pasal.source.local.model.Product

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(
        val products: List<Product>,
        val recentOrders: List<Order>,
        val stats: DashBoardStats,
        val ownerName: String,
        val shopName: String
    ) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}