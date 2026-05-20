package com.ananta.pasal.source.local.model

data class OrderItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val pricePerUnit: Double
)
