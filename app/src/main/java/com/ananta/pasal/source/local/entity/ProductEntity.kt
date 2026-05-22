package com.ananta.pasal.source.local.entity

import androidx.room.PrimaryKey
import androidx.room.Entity


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val ownerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val stockQuantity: Int,
    val category: String,
    val imageUrl: String?,
    val localImagePath: String?,
    val isDeleted: Boolean = false,
    val syncStatus: SyncStatus = SyncStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
