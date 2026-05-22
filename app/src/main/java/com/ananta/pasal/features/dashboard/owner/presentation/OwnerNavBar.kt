package com.ananta.pasal.features.dashboard.owner.presentation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.ananta.pasal.navigation.NavRoutes

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val ownerNavItems = listOf(
    BottomNavItem("Dashboard", Icons.Default.Dashboard, NavRoutes.OWNER_HOME.route),
    BottomNavItem("Inventory", Icons.Default.Inventory, NavRoutes.INVENTORY.route),
    BottomNavItem("Orders",    Icons.Default.ShoppingBag, NavRoutes.OWNER_ORDERS.route)
)

@Composable
fun OwnerBottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        ownerNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick  = { onNavigate(item.route) },
                icon     = { Icon(item.icon, contentDescription = item.label) },
                label    = { Text(item.label) }
            )
        }
    }
}