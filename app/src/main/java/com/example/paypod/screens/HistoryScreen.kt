package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paypod.R

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderHistory()
        HistoryContent()
    }
}

@Composable
fun HeaderHistory() {
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
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black
        )
        Text(
            text = "History",
            fontSize = 24.sp,
            color = Color.Black,
            style = TextStyle(fontFamily = orbitron)
        )
    }
}

@Composable
fun HistoryContent() {
    // Make the content scrollable
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        HistoryTransactionItem(date = "19-06-2024", amount = "243.00", username = "OABOUZOHOR", status = "Completed")
        HistoryTransactionItem(date = "18-06-2024", amount = "103.00", username = "OABOUZOHOR", status = "Pending")
        HistoryTransactionItem(date = "17-06-2024", amount = "201.00", username = "OABOUZOHOR", status = "Approved")
        HistoryTransactionItem(date = "16-06-2024", amount = "298.00", username = "OABOUZOHOR", status = "Declined")
        HistoryTransactionItem(date = "15-06-2024", amount = "120.00", username = "OABOUZOHOR", status = "Completed")
        HistoryTransactionItem(date = "19-06-2024", amount = "243.00", username = "OABOUZOHOR", status = "Completed")
        HistoryTransactionItem(date = "18-06-2024", amount = "103.00", username = "OABOUZOHOR", status = "Pending")
        HistoryTransactionItem(date = "17-06-2024", amount = "201.00", username = "OABOUZOHOR", status = "Approved")
        HistoryTransactionItem(date = "16-06-2024", amount = "298.00", username = "OABOUZOHOR", status = "Declined")
        HistoryTransactionItem(date = "15-06-2024", amount = "120.00", username = "OABOUZOHOR", status = "Completed")
        HistoryTransactionItem(date = "19-06-2024", amount = "243.00", username = "OABOUZOHOR", status = "Completed")
        HistoryTransactionItem(date = "18-06-2024", amount = "103.00", username = "OABOUZOHOR", status = "Pending")
        HistoryTransactionItem(date = "17-06-2024", amount = "201.00", username = "OABOUZOHOR", status = "Approved")
        HistoryTransactionItem(date = "16-06-2024", amount = "298.00", username = "OABOUZOHOR", status = "Declined")
        HistoryTransactionItem(date = "15-06-2024", amount = "120.00", username = "OABOUZOHOR", status = "Completed")
        HistoryTransactionItem(date = "19-06-2024", amount = "243.00", username = "OABOUZOHOR", status = "Completed")
        HistoryTransactionItem(date = "18-06-2024", amount = "103.00", username = "OABOUZOHOR", status = "Pending")
        HistoryTransactionItem(date = "17-06-2024", amount = "201.00", username = "OABOUZOHOR", status = "Approved")
        HistoryTransactionItem(date = "16-06-2024", amount = "298.00", username = "OABOUZOHOR", status = "Declined")
        HistoryTransactionItem(date = "15-06-2024", amount = "120.00", username = "OABOUZOHOR", status = "Completed")
    }
}

@Composable
fun HistoryTransactionItem(date: String, amount: String, username: String, status: String) {
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
