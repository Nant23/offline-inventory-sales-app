package com.ananta.pasal.features.dashboard.domain.repository

import com.ananta.pasal.features.dashboard.domain.model.DashBoardStats
import com.ananta.pasal.source.local.model.Order
import com.ananta.pasal.source.local.model.Product
import kotlinx.coroutines.flow.Flow

interface OwnerRepository {
    fun getProducts(ownerId: String): Flow<List<Product>>
    fun getOrders(ownerId: String): Flow<List<Order>>
    fun getDashboardStats(ownerId: String): Flow<DashBoardStats>
}