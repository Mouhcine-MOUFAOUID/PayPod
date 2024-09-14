package com.example.paypod.navbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.example.paypod.R

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color(0xFFFAFAFA)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val blue = colorResource(id = R.color.blue)

        listOf(BottomNavItem.Home, BottomNavItem.Payment, BottomNavItem.History, BottomNavItem.Settings, BottomNavItem.Profile)
            .forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route, navOptions {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        })
                    },
                    icon = { Icon(item.icon, contentDescription = item.label, tint = Color.Black) },
                    label = { Text(text = item.label, color = Color.Black) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedTextColor = Color.Black,
                        indicatorColor = blue
                    )
                )
            }
    }
}
