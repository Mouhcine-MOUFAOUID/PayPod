package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paypod.R

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderSection()
        UserInfoSection(username = "MMOUFAOUID")
        BalanceAmountSection(balance = "18.654,98 MAD")
        RecentTransactionsSection()
    }
}

@Composable
fun HeaderSection() {
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
        Text(
            text = "PayPod",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = Color.Black
        )
    }
}

@Composable
fun UserInfoSection(username: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = username,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Balance Amount",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun BalanceAmountSection(balance: String) {
    Text(
        text = balance,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun RecentTransactionsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TransactionItem(date = "19-06-2024", amount = "243.00", username = "OABOUZOHOR", status = "Completed")
        TransactionItem(date = "18-06-2024", amount = "103.00", username = "OABOUZOHOR", status = "Pending")
        TransactionItem(date = "17-06-2024", amount = "201.00", username = "OABOUZOHOR", status = "Approved")
        TransactionItem(date = "16-06-2024", amount = "298.00", username = "OABOUZOHOR", status = "Declined")
        TransactionItem(date = "15-06-2024", amount = "120.00", username = "OABOUZOHOR", status = "Completed")
        Text(
            text = "View All",
            color = Color.Blue,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun TransactionItem(date: String, amount: String, username: String, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = date, fontSize = 14.sp, color = Color.Black)
        Text(text = amount, fontSize = 14.sp, color = Color.Black)
        Text(text = username, fontSize = 14.sp, color = Color.Black)
        Text(text = status, fontSize = 14.sp, color = Color.Black)
    }
}