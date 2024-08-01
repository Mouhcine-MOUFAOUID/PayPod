// NavigationHost.kt
package com.example.paypod.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.paypod.screens.*

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.History.route) {
            HistoryScreen()
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
        composable(BottomNavItem.Settings.route) {
            SettingsScreen()
        }
        composable(BottomNavItem.Payment.route) {
            PaymentScreen(navController)
        }
        composable("success_screen/{amount}") { backStackEntry ->
            val amount = backStackEntry.arguments?.getString("amount") ?: ""
            SuccessScreen(navController, amount)
        }

        composable("scan_screen/{amount}") { backStackEntry ->
            val amount = backStackEntry.arguments?.getString("amount") ?: ""
            ScanScreen(navController = navController, amount = amount)
        }
    }
}
