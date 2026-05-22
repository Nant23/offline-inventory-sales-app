package com.ananta.pasal.features.dashboard.owner.data.repository

import com.ananta.pasal.features.dashboard.owner.domain.model.DashBoardStats
import com.ananta.pasal.features.dashboard.owner.domain.repository.OwnerRepository
import com.ananta.pasal.source.local.dao.OrderDao
import com.ananta.pasal.source.local.dao.ProductDao
import com.ananta.pasal.source.local.entity.OrderStatus
import com.ananta.pasal.source.local.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import com.ananta.pasal.source.local.mapper.toDomain
import com.ananta.pasal.source.local.model.Order
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

@Singleton
class OwnerRepositoryImpl@Inject constructor(
    private val productDao: ProductDao,
    private val orderDao: OrderDao
): OwnerRepository {

    override fun getProducts(ownerId: String): Flow<List<Product>> =
        productDao.getAllProducts(ownerId)
            .map { list -> list.map { it.toDomain() } }

    override fun getOrders(ownerId: String): Flow<List<Order>> =
        orderDao.getOrdersForOwner(ownerId)
            .map { list -> list.map { it.toDomain() } }

    override fun getDashboardStats(ownerId: String): Flow<DashBoardStats> =
        combine(
            productDao.getAllProducts(ownerId),
            orderDao.getOrdersForOwner(ownerId)
        ) { products, orders ->
            DashBoardStats(
                totalProducts     = products.size,
                outOfStockCount   = products.count { it.stockQuantity == 0 },
                pendingOrdersCount = orders.count { it.status == OrderStatus.PENDING },
                totalRevenue      = orders
                    .filter { it.status == OrderStatus.DELIVERED }
                    .sumOf { it.totalPrice }
            )
        }
}