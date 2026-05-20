package com.ananta.pasal.features.dashboard.domain.model

data class DashBoardStats(
    val totalProducts: Int,
    val outOfStockCount: Int,
    val pendingOrdersCount: Int,
    val totalRevenue: Double
)
