package com.ananta.pasal.navigation

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ananta.pasal.features.authentication.domain.model.UserRole
import com.ananta.pasal.features.authentication.presentation.auth.AuthState
import com.ananta.pasal.features.authentication.presentation.auth.LoginScreen
import com.ananta.pasal.features.authentication.presentation.auth.LoginViewModel
import com.ananta.pasal.features.authentication.presentation.auth.RegisterScreen
import com.ananta.pasal.features.authentication.presentation.auth.RegisterViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ananta.pasal.features.owner.presentation.dashboard.DashboardScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.LOGIN
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val registerViewModel: RegisterViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                viewModel = loginViewModel,
                onNavigateToRegister = {
                    navController.navigate(NavRoutes.REGISTER)
                },
                onLoginSuccess = {
                    val user = (loginViewModel.loginState.value as? AuthState.Success)?.user
                    if (user?.role == UserRole.OWNER) {
                        navController.navigate(NavRoutes.OWNER_HOME) {
                            popUpTo(NavRoutes.LOGIN) { inclusive = true }
                        }
                    } else {
                        navController.navigate(NavRoutes.CUSTOMER_HOME) {
                            popUpTo(NavRoutes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(NavRoutes.REGISTER) {
            RegisterScreen(
                viewModel = registerViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    val user = (registerViewModel.registerState.value as? AuthState.Success)?.user
                    if (user?.role == UserRole.OWNER) {
                        navController.navigate(NavRoutes.OWNER_HOME) {
                            popUpTo(NavRoutes.REGISTER) { inclusive = true }
                        }
                    } else {
                        navController.navigate(NavRoutes.CUSTOMER_HOME) {
                            popUpTo(NavRoutes.REGISTER) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(NavRoutes.OWNER_HOME) {
            DashboardScreen(
                onNavigateToInventory = { navController.navigate(NavRoutes.INVENTORY) },
                onNavigateToOrders = { navController.navigate(NavRoutes.OWNER_ORDERS) },
                onLogout = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(NavRoutes.CUSTOMER_HOME) {
            Surface {
                Text(text = "Customer Home Screen")
            }
        }
    }
}
