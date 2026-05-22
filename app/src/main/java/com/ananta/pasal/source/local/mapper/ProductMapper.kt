// data/local/mapper/ProductMapper.kt
package com.ananta.pasal.source.local.mapper
import com.ananta.pasal.source.local.entity.ProductEntity
import com.ananta.pasal.source.local.entity.SyncStatus
import com.ananta.pasal.source.local.model.Product

// Entity → Domain
fun ProductEntity.toDomain(): Product = Product(
    id = id,
    ownerId = ownerId,
    name = name,
    description = description,
    price = price,
    stockQuantity = stockQuantity,
    category = category,
    imageUrl = imageUrl,
    localImagePath = localImagePath,
    isDeleted = isDeleted,
    syncStatus = syncStatus,
    createdAt = createdAt,
    updatedAt = updatedAt
)

// Domain → Entity
fun Product.toEntity(syncStatus: SyncStatus = SyncStatus.PENDING): ProductEntity = ProductEntity(
    id = id,
    ownerId = ownerId,
    name = name,
    description = description,
    price = price,
    stockQuantity = stockQuantity,
    category = category,
    imageUrl = imageUrl,
    localImagePath = localImagePath,
    isDeleted = isDeleted,
    syncStatus = syncStatus,
    createdAt = createdAt,
    updatedAt = System.currentTimeMillis()
)