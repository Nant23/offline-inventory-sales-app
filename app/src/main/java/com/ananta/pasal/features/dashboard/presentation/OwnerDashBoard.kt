// features/owner/presentation/dashboard/DashboardScreen.kt
package com.ananta.pasal.features.owner.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ananta.pasal.features.dashboard.domain.model.DashBoardStats
import com.ananta.pasal.source.local.model.Order
import com.ananta.pasal.source.local.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToInventory: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (val state = uiState) {
                        is DashboardUiState.Success -> Column {
                            Text(state.shopName, style = MaterialTheme.typography.titleMedium)
                            Text(
                                "Hello, ${state.ownerName}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        else -> Text("Dashboard")
                    }
                },
//                actions = {
//                    IconButton(onClick = {
//                        viewModel.logout()
//                        onLogout()
//                    }) {
//                        Icon(Icons.Default.Logout, contentDescription = "Logout")
//                    }
//                }
            )
        },
    ) { padding ->
        when (val state = uiState) {
            is DashboardUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DashboardUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                }
            }

            is DashboardUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    // stats cards
                    item {
                        Text(
                            "Overview",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        StatsGrid(stats = state.stats)
                    }

                    // recent orders
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Recent Orders",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            TextButton(onClick = onNavigateToOrders) {
                                Text("See all")
                            }
                        }
                    }

                    if (state.recentOrders.isEmpty()) {
                        item {
                            Text(
                                "No orders yet",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        items(state.recentOrders) { order ->
                            OrderCard(order = order)
                        }
                    }

                    // low stock warning
                    val lowStock = state.products.filter { it.stockQuantity == 0 }
                    if (lowStock.isNotEmpty()) {
                        item {
                            Text(
                                "Out of Stock",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        items(lowStock) { product ->
                            OutOfStockCard(product = product)
                        }
                    }

                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            }
        }
    }
}

// --- Sub-components ---

@Composable
fun StatsGrid(stats: DashBoardStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            label = "Products",
            value = stats.totalProducts.toString(),
            icon = Icons.Default.Inventory,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
        StatCard(
            modifier = Modifier.weight(1f),
            label = "Pending Orders",
            value = stats.pendingOrdersCount.toString(),
            icon = Icons.Default.Pending,
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            label = "Out of Stock",
            value = stats.outOfStockCount.toString(),
            icon = Icons.Default.Warning,
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
        StatCard(
            modifier = Modifier.weight(1f),
            label = "Revenue",
            value = "NPR ${stats.totalRevenue.toInt()}",
            icon = Icons.Default.AttachMoney,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(order.customerName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(
                    "${order.items.size} item(s) • NPR ${order.totalPrice.toInt()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            StatusChip(status = order.status)
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (color, label) = when (status) {
        "PENDING"   -> MaterialTheme.colorScheme.errorContainer to "Pending"
        "CONFIRMED" -> MaterialTheme.colorScheme.secondaryContainer to "Confirmed"
        "DELIVERED" -> MaterialTheme.colorScheme.primaryContainer to "Delivered"
        else        -> MaterialTheme.colorScheme.surfaceVariant to status
    }
    Surface(
        shape = RoundedCornerShape(50),
        color = color
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun OutOfStockCard(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(product.name, style = MaterialTheme.typography.titleSmall)
            Text(
                "Out of Stock",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}