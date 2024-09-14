package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.paypod.R

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderSettings(navController)
        SettingsOptionsSection()
        Spacer(modifier = Modifier.weight(1f))
        LogoutButton()
    }
}

@Composable
fun HeaderSettings(navController: NavController) {
    val orbitron = FontFamily(
        Font(R.font.orbitronbold),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
        Text(
            text = "Settings",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
    }
}

@Composable
fun SettingsOptionsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SettingItem(label = "Notifications", icon = Icons.Default.Notifications)
        SettingItem(label = "Privacy", icon = Icons.Default.Lock)
        SettingItem(label = "About", icon = Icons.Default.Info)
        SettingItem(label = "Help", icon = Icons.AutoMirrored.Filled.Help)
    }
}

@Composable
fun SettingItem(label: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            //.background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Black,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LogoutButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            //.background(Color.White, shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Handle logout click */ }) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Log Out",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Log Out",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
