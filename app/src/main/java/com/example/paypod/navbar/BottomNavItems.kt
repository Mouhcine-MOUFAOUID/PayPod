package com.example.paypod.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    data object History : BottomNavItem("history", Icons.AutoMirrored.Filled.List, "History")
    data object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
    data object Settings : BottomNavItem("settings", Icons.Default.Settings, "Settings")
    data object Payment : BottomNavItem("payment", Icons.Default.CreditCard, "Payment")
    data object Scan : BottomNavItem("scan/{amount}", Icons.Default.Nfc, "Scan")
    data object Success : BottomNavItem("success_screen/{amount}", Icons.Default.Check, "Success")
    data object Confirm : BottomNavItem("confirm", Icons.Default.Shield, "Confirm")
}
