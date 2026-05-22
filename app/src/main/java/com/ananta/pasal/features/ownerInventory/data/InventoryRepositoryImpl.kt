package com.ananta.pasal.features.ownerInventory.data

import com.ananta.pasal.features.ownerInventory.domain.InventoryRepository
import com.ananta.pasal.source.local.entity.SyncStatus
import com.ananta.pasal.source.local.mapper.toEntity

import com.ananta.pasal.source.local.dao.OrderDao
import com.ananta.pasal.source.local.dao.ProductDao
import com.ananta.pasal.source.local.mapper.toDomain
import com.ananta.pasal.source.local.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InventoryRepositoryImpl@Inject constructor(
    private val productDao: ProductDao,
): InventoryRepository {

    override fun getProducts(ownerId: String): Flow<List<Product>> =
        productDao.getAllProducts(ownerId)
            .map { list -> list.map { it.toDomain() } }

    // in OwnerRepositoryImpl.kt
    override suspend fun addProduct(product: Product) {
        productDao.insert(product.toEntity(syncStatus = SyncStatus.PENDING))
    }

    override suspend fun updateProduct(product: Product) {
        productDao.update(product.toEntity(syncStatus = SyncStatus.PENDING))
    }

    override suspend fun deleteProduct(productId: String) {
        productDao.softDelete(productId)
    }
}