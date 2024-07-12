package com.example.paypod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.example.paypod.ui.theme.PayPodTheme
import com.example.paypod.ui.theme.bottomNavItems

class BottomNav : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navbar)

        val composeView = findViewById<ComposeView>(R.id.compose_container)
        composeView.setContent {
            PayPodTheme {
                var selected by remember { mutableIntStateOf(0) }

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            bottomNavItems.forEachIndexed { index, bottomNavItem ->
                                NavigationBarItem(
                                    selected = index == selected,
                                    onClick = { selected = index },
                                    icon = {
                                        BadgedBox(badge = {
                                            if (bottomNavItem.hasNews) {
                                                Badge {}
                                            }
                                        }) {
                                            Icon(
                                                imageVector = if (index == selected) {
                                                    bottomNavItem.selectedIcon
                                                } else {
                                                    bottomNavItem.unselectedIcon
                                                },
                                                contentDescription = bottomNavItem.title
                                            )
                                        }
                                    },
                                    label = {
                                        Text(text = bottomNavItem.title)
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    // Add your content here, using innerPadding for proper padding
                    Box(modifier = Modifier.padding(innerPadding)) {
                        // Your other composables can go here if needed
                    }
                }
            }
        }
    }
}
