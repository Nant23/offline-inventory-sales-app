package com.ananta.pasal.source.local.model

import com.ananta.pasal.source.local.entity.SyncStatus

data class Order(
    val id: String,
    val customerId: String,
    val customerName: String,
    val customerPhone: String,
    val ownerId: String,
    val shopName: String,
    val deliveryAddress: String,
    val items: List<OrderItem>,
    val totalPrice: Double,
    val status: String,
    val syncStatus: SyncStatus,
    val createdAt: Long,
    val updatedAt: Long
)
