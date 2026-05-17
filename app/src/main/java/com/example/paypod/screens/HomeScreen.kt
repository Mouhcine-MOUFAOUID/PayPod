package com.example.paypod.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
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
import com.example.paypod.model.TransactionDTO
import com.example.paypod.activities.sendRequest

@Composable
fun HomeScreen() {
    val recentTransactions = remember { mutableStateOf(listOf<TransactionDTO>()) }

    // Trigger the API request to fetch the recent transactions
    LaunchedEffect(Unit) {
        sendRequest("someId", recentTransactions)  // Adjust this based on your API call logic
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        HeaderSection()
        UserInfoSection(username = "MMOUFAOUID")
        BalanceAmountSection(balance = "18.654,98 MAD")
        RecentTransactionsSection(transactions = recentTransactions.value)
        Spacer(modifier = Modifier.height(16.dp))  // Space between transactions and card
        CurrencyExchangeCard()  // Added Currency Exchange Card
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
fun RecentTransactionsSection(transactions: List<TransactionDTO>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Only show the last 5 transactions
        val recentTransactions = transactions.takeLast(5)

        recentTransactions.forEach { transaction ->
            TransactionItem(
                date = transaction.dateTimeLocalTransaction,
                amount = "%.2f".format(transaction.transactionAmount),
                username = transaction.primaryAcountNumber.takeLast(4),  // Mask or display PAN appropriately
                status = transaction.status
            )
        }

//        Text(
//            text = "View All",
//            color = Color.Blue,
//            modifier = Modifier.align(Alignment.End)
//        )
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
        Text(text = status, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun CurrencyExchangeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Currency Exchange Rates",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Sample currency rates
            CurrencyRateRow(currency = "USD", rate = "1 USD = 10.5 MAD")
            CurrencyRateRow(currency = "EUR", rate = "1 EUR = 11.2 MAD")
            CurrencyRateRow(currency = "GBP", rate = "1 GBP = 12.9 MAD")
            CurrencyRateRow(currency = "CAD", rate = "1 CAD = 7.8 MAD")
        }
    }
}

@Composable
fun CurrencyRateRow(currency: String, rate: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = currency, fontSize = 16.sp, color = Color.Black)
        Text(text = rate, fontSize = 16.sp, color = Color.Black)
    }
}
