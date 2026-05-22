package com.ananta.pasal.features.ownerInventory.domain

import com.ananta.pasal.source.local.model.Product
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun getProducts(ownerId: String): Flow<List<Product>>
    suspend fun addProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(productId: String)
}
