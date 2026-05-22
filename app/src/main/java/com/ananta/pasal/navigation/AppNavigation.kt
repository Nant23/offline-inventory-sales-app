package com.ananta.pasal.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ananta.pasal.features.authentication.domain.model.UserRole
import com.ananta.pasal.features.authentication.presentation.auth.LoginScreen
import com.ananta.pasal.features.authentication.presentation.auth.LoginViewModel
import com.ananta.pasal.features.authentication.presentation.auth.RegisterScreen
import com.ananta.pasal.features.authentication.presentation.auth.RegisterViewModel
import com.ananta.pasal.features.dashboard.owner.presentation.OwnerBottomNavBar
import com.ananta.pasal.features.owner.presentation.dashboard.OwnerDashboardScreen
import com.ananta.pasal.features.owner.presentation.inventory.InventoryScreen

val ownerBottomBarScreens = setOf(
    NavRoutes.OWNER_HOME,
    NavRoutes.INVENTORY,
    NavRoutes.OWNER_ORDERS
)

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.LOGIN.route
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    val currentScreen = NavRoutes.entries.find {
        it.route == currentRoute
    }
    val showBottomBar = currentScreen in ownerBottomBarScreens

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                OwnerBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(NavRoutes.OWNER_HOME.route) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController    = navController,
            startDestination = startDestination,
            modifier         = Modifier.padding(padding)
        ) {
            composable(NavRoutes.LOGIN.route) {
                val loginViewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(
                    loginViewModel = loginViewModel,
                    onNavigateToRegister = { navController.navigate(NavRoutes.REGISTER.route) },
                    onLoginSuccess       = { user ->
                        val route = if (user.role == UserRole.OWNER)
                            NavRoutes.OWNER_HOME.route else NavRoutes.CUSTOMER_HOME.route
                        navController.navigate(route) {
                            popUpTo(NavRoutes.LOGIN.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(NavRoutes.REGISTER.route) {
                RegisterScreen(
                    onNavigateToLogin = { navController.popBackStack() },
                    onRegisterSuccess = { user ->
                        val route = if (user.role == UserRole.OWNER)
                            NavRoutes.OWNER_HOME.route else NavRoutes.CUSTOMER_HOME.route
                        navController.navigate(route) {
                            popUpTo(NavRoutes.REGISTER.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(NavRoutes.OWNER_HOME.route) {
                OwnerDashboardScreen(
                    //onNavigateToOrders    = { navController.navigate(NavRoutes.OWNER_ORDERS) },
//                    onLogout              = {
//                        navController.navigate(NavRoutes.LOGIN) {
//                            popUpTo(0) { inclusive = true }
//                        }
//                    }
                )
            }

            composable(NavRoutes.INVENTORY.route) {
                InventoryScreen()
            }

            composable(NavRoutes.CUSTOMER_HOME.route) {
                Surface { Text("Customer Home Screen") }
            }
        }
    }
}