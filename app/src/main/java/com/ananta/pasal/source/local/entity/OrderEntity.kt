package com.ananta.pasal.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val customerId: String,
    val customerName: String,
    val customerPhone: String,
    val ownerId: String,
    val shopName: String,
    val deliveryAddress: String,
    val itemsJson: String,
    val totalPrice: Double,
    val status: String = OrderStatus.PENDING,
    val syncStatus: SyncStatus = SyncStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
