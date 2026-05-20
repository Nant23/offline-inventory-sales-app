package com.ananta.pasal.source.local.model

data class Product(
    val id: String,
    val ownerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val stockQuantity: Int,
    val category: String,
    val imageUrl: String?,
    val localImagePath: String?,
    val isDeleted: Boolean,
    val syncStatus: String,
    val createdAt: Long,
    val updatedAt: Long
)
