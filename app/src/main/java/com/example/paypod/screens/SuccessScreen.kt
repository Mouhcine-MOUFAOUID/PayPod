package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Sms
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.paypod.R

@Composable
fun SuccessScreen(navController: NavController, amount: String) {
    val montserrat = FontFamily(
        Font(R.font.montserratmedium),
    )
    val montserratbold = FontFamily(
        Font(R.font.montserratextrabold),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = Color.Black,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Paid $amount MAD",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = montserratbold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "The transaction was successful!",
            fontSize = 16.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = montserrat)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { /* Handle send SMS */ },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Default.Sms,
                contentDescription = "Send SMS",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Send SMS",
                color = Color.Black,
                fontSize = 16.sp,
                style = TextStyle(fontFamily = montserrat)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle send Email */ },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Send Email",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Send Email",
                color = Color.Black,
                fontSize = 16.sp,
                style = TextStyle(fontFamily = montserrat)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("HomeScreen") },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(
                text = "Finish",
                color = Color.White,
                fontSize = 16.sp,
                style = TextStyle(fontFamily = montserrat)
            )
        }
    }
}
