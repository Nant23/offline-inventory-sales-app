package com.ananta.pasal.features.ownerInventory.presentation

import com.ananta.pasal.source.local.model.Product

// UI state
sealed class InventoryUiState {
    object Loading : InventoryUiState()
    data class Success(val products: List<Product>) : InventoryUiState()
    data class Error(val message: String) : InventoryUiState()
}
