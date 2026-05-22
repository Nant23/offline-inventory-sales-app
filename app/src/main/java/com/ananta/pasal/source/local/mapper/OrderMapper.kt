package com.ananta.pasal.source.local.mapper

import com.ananta.pasal.source.local.entity.OrderEntity
import com.ananta.pasal.source.local.entity.SyncStatus
import com.ananta.pasal.source.local.model.Order
import com.ananta.pasal.source.local.model.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()

// Entity → Domain
fun OrderEntity.toDomain(): Order = Order(
    id = id,
    customerId = customerId,
    customerName = customerName,
    customerPhone = customerPhone,
    ownerId = ownerId,
    shopName = shopName,
    deliveryAddress = deliveryAddress,
    items = gson.fromJson(itemsJson, object : TypeToken<List<OrderItem>>() {}.type),
    totalPrice = totalPrice,
    status = status,
    syncStatus = syncStatus,
    createdAt = createdAt,
    updatedAt = updatedAt
)

// Domain → Entity
fun Order.toEntity(syncStatus: SyncStatus = SyncStatus.PENDING): OrderEntity = OrderEntity(
    id = id,
    customerId = customerId,
    customerName = customerName,
    customerPhone = customerPhone,
    ownerId = ownerId,
    shopName = shopName,
    deliveryAddress = deliveryAddress,
    itemsJson = gson.toJson(items),
    totalPrice = totalPrice,
    status = status,
    syncStatus = syncStatus,
    createdAt = createdAt,
    updatedAt = System.currentTimeMillis()
)